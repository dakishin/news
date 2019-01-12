package ru.soft.news.gui.news.list

import ru.soft.news.model.News


/**
 *   Created by dakishin@gmail.com
 *   ViewModel для экрана список новостей
 */

data class NewsListViewModel(val news: List<News> = arrayListOf(),
    val state: State? = null, val error: String? = null) {
  enum class State {
    OK,
    LOADING,
    REFRESHING,
    ERROR
  }
}