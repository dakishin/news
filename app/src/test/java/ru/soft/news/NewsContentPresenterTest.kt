package ru.soft.news

import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import ru.soft.news.gui.news.content.NewsContentPresenter
import ru.soft.news.gui.news.content.NewsContentViewModel
import ru.soft.news.interactors.ApiInteractor
import ru.soft.news.interactors.StorageInteractor
import ru.soft.news.model.NewsContent

/**
 *   Created by dakishin@gmail.com
 */
class NewsContentPresenterTest : BaseTestCase() {

    @Mock
    lateinit var apiInteractor: ApiInteractor
    @Mock
    lateinit var storageInteractor: StorageInteractor

    lateinit var newsContentPresenter: NewsContentPresenter

    @Before
    override fun setUp() {
        super.setUp()
        newsContentPresenter = NewsContentPresenter(apiInteractor, storageInteractor)
    }

    @Test
    fun shouldShowNewsContent() {

//      Хранилище отдает закешированные данные
        Mockito.`when`(storageInteractor.getNewsContent("id")).thenReturn(Maybe.just(newsContent))
        Mockito.`when`(apiInteractor.getNewsContent("id")).thenReturn(Single.error(RuntimeException()))


        newsContentPresenter.loadNewsContentUseCase("id")
                .test()
                .assertValues(
                        NewsContentViewModel(state = NewsContentViewModel.State.LOADING),
                        NewsContentViewModel(state = NewsContentViewModel.State.OK, content = newsContent)
                )

//      В хранилище ошибка, загружаем из сети
        Mockito.`when`(storageInteractor.getNewsContent("id")).thenReturn(Maybe.error(RuntimeException()))
        Mockito.`when`(apiInteractor.getNewsContent("id")).thenReturn(Single.just(newsContent))


        newsContentPresenter.loadNewsContentUseCase("id")
                .test()
                .assertValues(
                        NewsContentViewModel(state = NewsContentViewModel.State.LOADING),
                        NewsContentViewModel(state = NewsContentViewModel.State.OK, content = newsContent)
                )

//TODO: и тд.

    }


    val newsContent = NewsContent("", "", "")

}