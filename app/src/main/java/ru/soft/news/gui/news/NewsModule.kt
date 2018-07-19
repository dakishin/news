package ru.soft.news.gui.news

import ru.soft.news.gui.news.content.NewsContentPresenter
import ru.soft.news.gui.news.list.NewsListPresenter
import ru.soft.news.interactors.ApiInteractor
import ru.soft.news.interactors.StorageInteractor

/**
 *   Created by dakishin@gmail.com
 *
 *   Класс производит объекты для viper модуля Новости
 */
class NewsModule(val storageInteractor: StorageInteractor, val apiInteractor: ApiInteractor) {

    fun provideNewsListPresenter(): NewsListPresenter {
        return NewsListPresenter(apiInteractor, storageInteractor)
    }

    fun provideNewsContentPresenter(): NewsContentPresenter {
        return NewsContentPresenter(apiInteractor, storageInteractor)
    }

    fun provideNewsRouter(): Router {
        return Router()
    }


}