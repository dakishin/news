package ru.soft.news.interactors

import io.reactivex.Single
import ru.soft.news.api.Service
import ru.soft.news.model.News
import ru.soft.news.model.NewsContent

/**
 *   Created by dakishin@gmail.com
 *
 *   Интерактор приводит модель апи объектов к доменной модели приложения.
 */
interface ApiInteractor {
  fun getNewsList(): Single<List<News>>
  fun getNewsContent(newsId: String): Single<NewsContent>

}

open class ApiInteractorImp(private val api: Service) : ApiInteractor {

  override fun getNewsList(): Single<List<News>> =
      api
          .getNewsList()
          .map {
            it.payload
                .map {
                  News(id = it.id, title = it.text,
                      publicationDate = it.publicationDate.milliseconds)
                }
                .sortedByDescending {
                  it.publicationDate
                }
          }


  override fun getNewsContent(newsId: String): Single<NewsContent> =
      api
          .getNewsContent(newsId)
          .map {
            NewsContent(id = it.payload.title.id, title = it.payload.title.text,
                content = it.payload.content)
          }


}