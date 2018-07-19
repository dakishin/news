package ru.soft.news

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import ru.soft.news.gui.news.list.NewsListPresenter
import ru.soft.news.gui.news.list.NewsListViewModel
import ru.soft.news.interactors.ApiInteractor
import ru.soft.news.interactors.StorageInteractor
import ru.soft.news.model.News

/**
 *   Created by dakishin@gmail.com
 */
class NewsListPresenterTest : BaseTestCase() {

    @Mock
    lateinit var apiInteractor: ApiInteractor
    @Mock
    lateinit var storageInteractor: StorageInteractor

    lateinit var newsListPresenter: NewsListPresenter

    @Before
    override fun setUp() {
        super.setUp()
        newsListPresenter = NewsListPresenter(apiInteractor, storageInteractor)
    }

    @Test
    fun shouldShowNewsList() {

//      Хранилище отдает закешированные данные
        Mockito.`when`(storageInteractor.getAllNews()).thenReturn(Maybe.just(listOf(news)))
        Mockito.`when`(apiInteractor.getNewsList()).thenReturn(Single.error(RuntimeException()))


        newsListPresenter.loadNewsUseCase()
                .test()
                .assertValues(
                        NewsListViewModel(state = NewsListViewModel.State.LOADING),
                        NewsListViewModel(state = NewsListViewModel.State.OK, news = listOf(news))
                )

//      В хранилище ошибка, загружаем из сети
        Mockito.`when`(storageInteractor.getAllNews()).thenReturn(Maybe.error(RuntimeException()))
        Mockito.`when`(apiInteractor.getNewsList()).thenReturn(Single.just(listOf(news)))

        newsListPresenter.loadNewsUseCase()
                .test()
                .assertValues(
                        NewsListViewModel(state = NewsListViewModel.State.LOADING),
                        NewsListViewModel(state = NewsListViewModel.State.OK, news = listOf(news))
                )

//TODO: и тд.

    }

    @Test
    fun shouldRefresh() {
//      Первый запуск. Нет интернета
        Mockito.`when`(storageInteractor.getAllNews()).thenReturn(Maybe.just(listOf()))
        Mockito.`when`(apiInteractor.getNewsList()).thenReturn(Single.error(RuntimeException("no internet")))
        Mockito.`when`(storageInteractor.deleteAllNews()).thenReturn(Completable.complete())
        Mockito.`when`(storageInteractor.deleteAllContent()).thenReturn(Completable.complete())

        newsListPresenter.refreshUseCase()
                .test()
                .assertValues(
                        NewsListViewModel(state = NewsListViewModel.State.ERROR, error = "no internet")
                )

    }

    val news = News("", "", 1)

}