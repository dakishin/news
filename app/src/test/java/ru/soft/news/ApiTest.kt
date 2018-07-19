package ru.soft.news

import io.reactivex.observers.TestObserver
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Test
import retrofit2.Retrofit
import ru.soft.news.api.newsClient
import ru.soft.news.interactors.ApiInteractor
import ru.soft.news.model.News
import ru.soft.news.model.NewsContent


class ApiTest {

    private val client: ApiInteractor

    init {
        val httpClient = OkHttpClient().newsClient(hashMapOf(Pair("User-Agent", "Android")), HttpLoggingInterceptor.Level.BODY)
        client = Retrofit.Builder().newsClient(httpClient, "https://api.tinkoff.ru/v1/")
    }

    @Test
    fun shouldGetNewsList() {

        val testSubscriber = TestObserver<List<News>>()

        client.getNewsList().subscribe(testSubscriber)

        testSubscriber.assertValue {
            it.isNotEmpty()
        }
    }

    @Test
    fun shouldGetNewsContent() {
        val id = "10024"

        val testSubscriber = TestObserver<NewsContent>()

        client.getNewsContent(id).subscribe(testSubscriber)

        testSubscriber.assertValueCount(1)
    }


}
