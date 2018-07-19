package ru.soft.news.gui.news.content

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ru.soft.news.interactors.ApiInteractor
import ru.soft.news.interactors.StorageInteractor
import ru.soft.news.viper.Presenter

/**
 *   Created by dakishin@gmail.com
 *
 *   Хранит liveData состояние для экрана Контент новости.
 *   Выполняет модификации этого состояния.
 */
class NewsContentPresenter(
        private val apiInteractor: ApiInteractor,
        private val storageInteractor: StorageInteractor) : Presenter<NewsContentViewModel>() {

    fun loadNewsContent(id: String) {
        dispatcher.onNext(loadNewsContentUseCase(id).subscribeOn(Schedulers.io()))
    }

//    открываем фабричные методы для use case, чтобы протестировать их
    fun loadNewsContentUseCase(id: String): Observable<NewsContentViewModel> =
            Observable.concat(
                    Observable.just(NewsContentViewModel(state = NewsContentViewModel.State.LOADING)),
                    storageInteractor.getNewsContent(id)
                            .onErrorComplete()
                            .switchIfEmpty(apiInteractor.getNewsContent(id)
                                    .doOnSuccess {
                                        storageInteractor.saveNewsContent(it)
                                    }).toObservable()
                            .map {
                                NewsContentViewModel(content = it, state = NewsContentViewModel.State.OK)
                            })
                    .onErrorReturn { NewsContentViewModel(error = it.message, state = NewsContentViewModel.State.ERROR) }

}