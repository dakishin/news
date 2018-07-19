package ru.soft.news.di

import android.content.Context
import ru.soft.news.gui.news.NewsComponent
import ru.soft.news.gui.news.NewsModule
import ru.soft.news.gui.news.Router
import ru.soft.news.gui.news.content.NewsContentPresenter
import ru.soft.news.gui.news.list.NewsListPresenter
import ru.soft.news.interactors.ApiInteractor
import ru.soft.news.interactors.StorageInteractor


/**
 *   Created by dakishin@gmail.com
 *   Даггер нельзя. Делаем свой контейнер.
 */
class IocContainer {

    private lateinit var appModule: AppModule
    private lateinit var apiInteractor: ApiInteractor
    private lateinit var storageIterator: StorageInteractor
    private lateinit var router: Router

    private lateinit var newsListPresenter: NewsListPresenter
    private lateinit var newsContentPresenter: NewsContentPresenter

    fun init(context: Context) {
        appModule = AppModule(context)
        apiInteractor = appModule.provideApiInteractor()
        storageIterator = appModule.provideStorageInteractor()


        val newsModule = NewsModule(storageIterator, apiInteractor)
        router = newsModule.provideNewsRouter()
        newsListPresenter = newsModule.provideNewsListPresenter()
        newsContentPresenter = newsModule.provideNewsContentPresenter()

    }

    fun inject(component: NewsComponent) {
        component.contentPresenter = newsContentPresenter
        component.listPresenter = newsListPresenter
        component.router = router
    }

}