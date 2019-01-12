package ru.soft.news.gui.news.content;

import ru.soft.news.interactors.ApiInteractor;
import ru.soft.news.interactors.StorageInteractor;

public class NewsContentModule {

  public NewsContentPresenter provideNewsContentPresenter(
      ApiInteractor apiInteractor,
      StorageInteractor storageInteractor) {
    return new NewsContentPresenter(apiInteractor, storageInteractor);
  }
}
