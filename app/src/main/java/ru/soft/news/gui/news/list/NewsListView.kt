package ru.soft.news.gui.news.list

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import ru.soft.news.BaseFragment
import ru.soft.news.NewsApplication
import ru.soft.news.R
import ru.soft.news.gui.news.Router
import ru.soft.news.gui.news.TAG
import ru.soft.news.model.News
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 *   Created by dakishin@gmail.com
 *
 *   View для экрана список новостей. View подлючается к презентеру и приводит в соответствие свое состояние.
 *   Механизм ViewModel data гарантирует модификацию view в подходящее время жизненного цикла фрагмента.
 *
 */

class NewsListView : BaseFragment() {

  private lateinit var presenter: NewsListPresenter
  private lateinit var router: Router

  private lateinit var recyclerView: RecyclerView
  private lateinit var newsAdapter: NewsListAdapter
  private lateinit var viewManager: RecyclerView.LayoutManager
  private lateinit var progressBar: View
  private lateinit var swipeRefresh: SwipeRefreshLayout


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_news_list, container, false)
  }


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewManager = LinearLayoutManager(context)

    newsAdapter = NewsListAdapter(router)
    recyclerView = view.findViewById<RecyclerView>(R.id.news_list).apply {
      setHasFixedSize(true)
      layoutManager = viewManager
      adapter = newsAdapter
    }
    progressBar = view.findViewById(R.id.progressBar)
    swipeRefresh = view.findViewById(R.id.swipe_refresh)
    swipeRefresh.setOnRefreshListener {
      presenter.refresh()
    }

    enableBackButton(false)


    presenter.liveData
        .observe(this, Observer {
          it ?: return@Observer
          renderVM(it)
        })
  }


  private fun renderVM(viewModel: NewsListViewModel) {

    when (viewModel.state) {
      NewsListViewModel.State.REFRESHING -> {
        progressBar.visibility = View.GONE
        swipeRefresh.isRefreshing = true
      }
      NewsListViewModel.State.LOADING -> {
        progressBar.visibility = View.VISIBLE
        swipeRefresh.isRefreshing = false
      }
      NewsListViewModel.State.OK -> {
        progressBar.visibility = View.GONE
        swipeRefresh.isRefreshing = false
        newsAdapter.addItems(viewModel.news)
      }
      NewsListViewModel.State.ERROR -> {
        progressBar.visibility = View.GONE
        swipeRefresh.isRefreshing = false
        Toast.makeText(context, viewModel.error, Toast.LENGTH_SHORT).show()
      }
    }
  }


  companion object {

    fun createInstance(): NewsListView {
      return NewsListView()
    }

  }

  class NewsListAdapter(
      private val router: Router) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {
    private val newsLists = arrayListOf<News>()
    private val simpleDateFormat = SimpleDateFormat("dd MMMM, HH:mm", Locale.getDefault())

    class ViewHolder(rootView: View, val titleView: TextView,
        val newsDateView: TextView) : RecyclerView.ViewHolder(rootView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

      val rootView = LayoutInflater.from(parent.context)
          .inflate(R.layout.news_list_item, parent, false)

      val newTitleView = rootView.findViewById<TextView>(R.id.newsTitle)
      val newsDateView = rootView.findViewById<TextView>(R.id.date)

      return ViewHolder(rootView, newTitleView, newsDateView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val news = newsLists[position]

      holder.titleView.text = Html.fromHtml(news.title)
      holder.newsDateView.text = simpleDateFormat.format(Date(news.publicationDate))
      holder.itemView.setOnClickListener {
        router.showDetails(news.id)
      }
    }

    override fun getItemCount() = newsLists.size

    fun addItems(news: List<News>) {
      newsLists.clear()
      newsLists.addAll(news)
      notifyDataSetChanged()
    }
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val newsListComponent = NewsApplication.appComponent().plusNewsListComponent()

    presenter = newsListComponent.presenter
    router = NewsApplication.appComponent().router

    presenter.loadNews()

    Log.d(TAG, "NewsListFragment created")
  }

  override fun onDestroy() {
    Log.d(TAG, "NewsListFragment destroyed")
    super.onDestroy()
  }
}
