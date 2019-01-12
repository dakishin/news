package ru.soft.news.gui.news.list

import android.util.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ru.soft.news.gui.news.TAG
import ru.soft.news.interactors.ApiInteractor
import ru.soft.news.interactors.StorageInteractor
import ru.soft.news.viper.Presenter

/**
 *   Created by dakishin@gmail.com
 *
 *   Хранит liveData состояние для экрана Список новостей.
 *   Выполняет модификации этого состояния.
 */
class NewsListPresenter(private val apiInteractor: ApiInteractor,
    private val storageInteractor: StorageInteractor) : Presenter<NewsListViewModel>() {


  public fun loadNews() {
    Log.d(TAG,"load news list")
    dispatcher.onNext(loadNewsUseCase().subscribeOn(Schedulers.io()))
  }

  fun refresh() {
    dispatcher.onNext(refreshUseCase().subscribeOn(Schedulers.io()))
  }

  //    открываем фабричные методы для use case, чтобы протестировать их
  fun loadNewsUseCase(): Observable<NewsListViewModel> =
      Observable.concat(
          Observable.just(NewsListViewModel(state = NewsListViewModel.State.LOADING)),

          storageInteractor
              .getAllNews()
              .onErrorComplete()
              .switchIfEmpty(
                  apiInteractor.getNewsList()
                      .doOnSuccess {
                        storageInteractor.saveNews(it)
                      }

              ).toObservable().map {
                NewsListViewModel(news = it, state = NewsListViewModel.State.OK)
              })
          .onErrorReturn {
            NewsListViewModel(error = it.message, state = NewsListViewModel.State.ERROR)
          }


  fun refreshUseCase() =
      storageInteractor
          .deleteAllContent()
          .andThen(storageInteractor.deleteAllNews())
          .andThen(apiInteractor.getNewsList()
              .doOnSuccess {
                storageInteractor.saveNews(it)
              }).toObservable().map {
            NewsListViewModel(news = it, state = NewsListViewModel.State.OK)
          }
          .onErrorReturn {
            NewsListViewModel(error = it.message, state = NewsListViewModel.State.ERROR)
          }


}