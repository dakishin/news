package ru.soft.news.viper

import android.support.v4.app.FragmentActivity
import ru.soft.news.R
import ru.soft.news.gui.news.RouteState
import ru.soft.news.gui.news.content.NewsContentView
import ru.soft.news.gui.news.list.NewsListView

/**
 *   Created by dakishin@gmail.com
 *
 * Клас работает с андроид апи. Выполняет навигацию.
 *
 */

val CONTENT_FRAGMENT_TAG = "content_fragment"
val LIST_FRAGMENT_TAG = "list_fragment"

class RouterProvider(private var activity: FragmentActivity) {


  fun route(route: RouteState) {
    when (route) {
      is RouteState.ShowList -> showNewsList()
      is RouteState.ShowContent -> showNewsDetail(route.id)
      is RouteState.Finish -> finish()
      is RouteState.Back -> back()
    }
  }

  private fun showNewsDetail(id: String) {
    val content = activity.supportFragmentManager.findFragmentByTag(CONTENT_FRAGMENT_TAG)
    if (content != null) {
//            мы уже в этом фрагменте
      return
    }

    val tr = activity.supportFragmentManager.beginTransaction()
    tr.replace(R.id.fragment_container, NewsContentView.newInstance(id), CONTENT_FRAGMENT_TAG)
    tr.addToBackStack("show_content_transaction")
    tr.commit()
  }

  private fun showNewsList() {
    val content = activity.supportFragmentManager.findFragmentByTag(LIST_FRAGMENT_TAG)
    if (content != null) {
//            мы уже в этом фрагменте
      return
    }
    val tr = activity.supportFragmentManager.beginTransaction()
    tr.replace(R.id.fragment_container, NewsListView.createInstance(), LIST_FRAGMENT_TAG)
    tr.commit()
  }

  private fun back() {
    activity.supportFragmentManager.popBackStack()
  }

  private fun finish() {
    activity.finish()
  }


}