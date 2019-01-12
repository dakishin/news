package ru.soft.news.dagger;

import ru.soft.news.gui.news.Router;
import ru.soft.news.gui.news.content.NewsContentComponent;
import ru.soft.news.gui.news.list.NewsListComponent;

public interface AppComponent {

  Router getRouter();

  NewsListComponent plusNewsListComponent();

  NewsContentComponent plusNewsContentComponent();
}
