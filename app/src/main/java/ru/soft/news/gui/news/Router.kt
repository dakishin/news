package ru.soft.news.gui.news

import android.arch.lifecycle.MutableLiveData

/**
 *   Created by dakishin@gmail.com
 */

sealed class RouteState {
    class ShowList : RouteState()
    class ShowContent(val id: String) : RouteState()
    class Back : RouteState()
    class Finish : RouteState()
}

class Router {
    val liveRoute = MutableLiveData<RouteState>()

    fun back() {
        liveRoute.value = RouteState.Back()
    }

    fun finish() {
        liveRoute.value = RouteState.Finish()
    }

    fun showNewsList() {
        liveRoute.value = RouteState.ShowList()
    }

    fun showDetails(id: String) {
        liveRoute.value = RouteState.ShowContent(id)
    }

}