package ru.soft.news.gui.news.content

import ru.soft.news.model.NewsContent


/**
 *   Created by dakishin@gmail.com
 *
 *  ViewModel для экрана контент новости
 */


data class NewsContentViewModel(val content: NewsContent? = null, val state: State? = null, val error: String? = null) {
    enum class State {
        OK,
        LOADING,
        ERROR
    }
}