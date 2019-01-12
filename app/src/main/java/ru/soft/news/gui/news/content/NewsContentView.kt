package ru.soft.news.gui.news.content

import android.arch.lifecycle.Observer
import android.os.Build.ID
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import ru.soft.news.BaseFragment
import ru.soft.news.NewsApplication
import ru.soft.news.R
import ru.soft.news.gui.news.Router
import ru.soft.news.gui.news.TAG


/**
 *   Created by dakishin@gmail.com
 *
 *   View для экрана контент новости. View подлючается к презентеру и приводит в соответствие свое состояние.
 *   Механизм ViewModel data гарантирует модификацию view в подходящее время жизненного цикла фрагмента.
 *
 */
val NEWS_ID = "news_id"
class NewsContentView : BaseFragment() {


  private lateinit var presenter: NewsContentPresenter
  private lateinit var router: Router

  lateinit var newsTitle: TextView

  private lateinit var progressBar: View
  private lateinit var newsContent: TextView


  companion object {
    fun newInstance(id: String): NewsContentView {
      val newsContentFragment = NewsContentView()
      newsContentFragment.arguments = Bundle().apply { putString(NEWS_ID, id) }
      return newsContentFragment
    }
  }


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_news_content, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    newsTitle = view.findViewById(R.id.newsTitle)
    progressBar = view.findViewById(R.id.progressBar)
    newsContent = view.findViewById(R.id.newsContent)
    enableBackButton(true)
    setHasOptionsMenu(true)
    newsContent.text = null

    presenter.liveData.observe(this, Observer {
      it ?: return@Observer
      renderVM(it)
    })

  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> {
        router.back()
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  private fun renderVM(viewModel: NewsContentViewModel) {
    when (viewModel.state) {
      NewsContentViewModel.State.LOADING -> {
        progressBar.visibility = View.VISIBLE
        newsTitle.text = null
        newsContent.text = null

      }
      NewsContentViewModel.State.OK -> {
        newsTitle.text = Html.fromHtml(viewModel.content!!.title)
        newsContent.text = Html.fromHtml(viewModel.content.content)
        progressBar.visibility = View.GONE
      }
      NewsContentViewModel.State.ERROR -> {
        progressBar.visibility = View.GONE
        Toast.makeText(context, viewModel.error, Toast.LENGTH_SHORT).show()
      }
    }
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val newsContentComponent = NewsApplication.appComponent().plusNewsContentComponent()

    presenter = newsContentComponent.presenter
    router = NewsApplication.appComponent().router

    presenter.loadNewsContent(arguments?.getString(NEWS_ID))

    Log.d(TAG, "NewsContentFragment created")
  }

  override fun onDestroy() {
    Log.d(TAG, "NewsContentFragment destroyed")
    super.onDestroy()
  }
}