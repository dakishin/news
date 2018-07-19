package ru.soft.news.gui.news

import android.arch.lifecycle.ViewModel
import ru.soft.news.gui.news.content.NewsContentPresenter
import ru.soft.news.gui.news.list.NewsListPresenter

/**
 *   Created by dakishin@gmail.com
 *
 *   Компонет хранит состояния:
 *   1. RouteState                      последнее навигационное действие пользователя на этой активити
 *   2. NewsListPresenter.liveData      состояние экрана списка новостей
 *   3. NewsContentPresenter.liveData   состяние экрана контент новости
 *
 *  Все работает по реактивному принципу.
 *  Активити подписывается на изменения RouteState
 *  Экраны подписываются на изменение своих  состояний через liveData соответствующих презентеров.
 *  Навигация и обновление view происходят только через NewsActivityPresenter. ViewModel гарантирует, что подписчики отработают в нужное время.
 *
 */
class NewsComponent : ViewModel() {

    lateinit var listPresenter: NewsListPresenter

    lateinit var contentPresenter: NewsContentPresenter

    lateinit var router: Router

}