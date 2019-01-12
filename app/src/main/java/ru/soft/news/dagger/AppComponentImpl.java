package ru.soft.news.dagger;

import ru.soft.news.gui.news.Router;
import ru.soft.news.gui.news.content.NewsContentComponent;
import ru.soft.news.gui.news.content.NewsContentModule;
import ru.soft.news.gui.news.content.NewsContentPresenter;
import ru.soft.news.gui.news.list.NewsListComponent;
import ru.soft.news.gui.news.list.NewsListModule;
import ru.soft.news.gui.news.list.NewsListPresenter;
import ru.soft.news.interactors.ApiInteractor;
import ru.soft.news.interactors.StorageInteractor;

public class AppComponentImpl implements AppComponent {
  private Provider<ApiInteractor> apiInteractorProvider;
  private Provider<StorageInteractor> storageInteractorProvider;
  private Provider<Router> routerProvider;

  @Override public Router getRouter() {
    return routerProvider.get();
  }

  @Override public NewsListComponent plusNewsListComponent() {
    return new NewsListComponentImpl(new NewsListModule());
  }

  @Override
  public NewsContentComponent plusNewsContentComponent() {
    return new NewsContentComponentImpl(new NewsContentModule());
  }

  public Builder builder() {
    return new Builder();
  }

  public class Builder {
    private AppModule appModule;

    public Builder withAppModule(AppModule appModule) {
      this.appModule = appModule;
      return this;
    }

    public AppComponent build() {
      AppComponentImpl.this.apiInteractorProvider =
          new SimpleProvider<>(appModule.provideApiInteractor());
      AppComponentImpl.this.storageInteractorProvider =
          new SimpleProvider<>(appModule.provideStorageInteractor());
      AppComponentImpl.this.routerProvider = new SimpleProvider<>(appModule.provideRouter());
      return AppComponentImpl.this;
    }
  }

  public class NewsListComponentImpl implements NewsListComponent {

    private final NewsListModule newsListModule;

    NewsListComponentImpl(NewsListModule newsListModule) {
      this.newsListModule = newsListModule;
    }

    @Override public NewsListPresenter getPresenter() {
      return newsListModule.provideNewsListPresenter(
          apiInteractorProvider.get(),
          storageInteractorProvider.get());
    }
  }

  public class NewsContentComponentImpl implements NewsContentComponent {

    private final NewsContentModule module;

    NewsContentComponentImpl(NewsContentModule newsListModule) {
      this.module = newsListModule;
    }

    @Override public NewsContentPresenter getPresenter() {
      return module.provideNewsContentPresenter(
          apiInteractorProvider.get(),
          storageInteractorProvider.get());
    }
  }
}
