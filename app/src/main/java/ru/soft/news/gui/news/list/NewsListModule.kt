package ru.soft.news.gui.news.list

import ru.soft.news.interactors.ApiInteractor
import ru.soft.news.interactors.StorageInteractor

/**
 *   Created by dakishin@gmail.com
 *
 */
class NewsListModule {

  fun provideNewsListPresenter(
      apiInteractor: ApiInteractor,
      storageInteractor: StorageInteractor): NewsListPresenter {
    return NewsListPresenter(apiInteractor, storageInteractor)
  }


}