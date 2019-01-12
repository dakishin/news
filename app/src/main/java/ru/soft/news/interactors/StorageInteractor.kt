package ru.soft.news.interactors

import io.reactivex.Completable
import io.reactivex.Maybe
import ru.soft.news.model.News
import ru.soft.news.model.NewsContent
import ru.soft.news.persistence.NewsContentRecord
import ru.soft.news.persistence.NewsDatabase
import ru.soft.news.persistence.NewsRecord

/**
 *   Created by dakishin@gmail.com
 *
 * Интерактор приводит модель базы данных к доменной модели приложения.
 */

interface StorageInteractor {
  fun getAllNews(): Maybe<List<News>>
  fun deleteAllNews(): Completable
  fun saveNews(news: List<ru.soft.news.model.News>)
  fun getNewsContent(newsId: String): Maybe<NewsContent>
  fun deleteAllContent(): Completable
  fun saveNewsContent(content: ru.soft.news.model.NewsContent)
}

class StorageInteractorImpl(private val database: NewsDatabase) : StorageInteractor {

  override fun getAllNews(): Maybe<List<News>> = Maybe.fromCallable {
    val news = database.newsDao().getAll().map(::convert)
    if (news.isEmpty()) {
      null
    } else {
      news
    }
  }


  override fun deleteAllNews() =
      Completable.fromAction {
        database.newsDao().deleteAll()
      }


  override fun saveNews(news: List<ru.soft.news.model.News>) {
    database.newsDao().save(news.map(::convert))
  }

  override fun getNewsContent(newsId: String) =
      Maybe.fromCallable<NewsContent> {
        val content = database.newsContentDao().getByNewsId(newsId)
        if (content == null) {
          null
        } else {
          convert(content)
        }
      }


  override fun saveNewsContent(content: ru.soft.news.model.NewsContent) {
    database.newsContentDao().save(convert(content))
  }

  override fun deleteAllContent() =
      Completable.fromAction {
        database.newsContentDao().deleteAll()
      }


  private fun convert(news: ru.soft.news.model.News): NewsRecord {
    return NewsRecord(id = news.id, title = news.title, publicationDate = news.publicationDate)
  }

  private fun convert(content: NewsContentRecord): NewsContent {
    return NewsContent(id = content.id, title = content.title!!, content = content.title!!)
  }

  private fun convert(newsRecord: NewsRecord): News {
    return News(id = newsRecord.id, title = newsRecord.title!!,
        publicationDate = newsRecord.publicationDate!!)
  }

  private fun convert(content: ru.soft.news.model.NewsContent): NewsContentRecord {
    return NewsContentRecord(id = content.id, title = content.title, content = content.content)

  }
}