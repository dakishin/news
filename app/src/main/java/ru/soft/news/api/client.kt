package ru.soft.news.api

import com.google.gson.GsonBuilder
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.soft.news.interactors.ApiInteractor
import ru.soft.news.interactors.ApiInteractorImp
import java.util.concurrent.TimeUnit


/**
 *   Created by dakishin@gmail.com
 */


interface Service {
    @GET("news")
    fun getNewsList(): Single<ResponseNewsList>

    @GET("news_content")
    fun getNewsContent(@Query("id") id: String): Single<ResponseNewsContent>
}


fun Retrofit.Builder.newsClient(client: OkHttpClient, url: String): ApiInteractor {
    val gson = GsonBuilder()
            .create()

    val factory = GsonConverterFactory.create(gson)
    this.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(factory)
            .client(client)
            .baseUrl(url)

    return ApiInteractorImp(this.build().create(Service::class.java))
}

fun OkHttpClient.newsClient(headers: Map<String, String>, logLevel: HttpLoggingInterceptor.Level): OkHttpClient {
    return this.newBuilder()
            .addHeaders(headers)
            .addLogger(logLevel)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
}

private fun OkHttpClient.Builder.addLogger(logLevel: HttpLoggingInterceptor.Level): OkHttpClient.Builder {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = logLevel
    this.addInterceptor(loggingInterceptor)
    return this
}

private fun OkHttpClient.Builder.addHeaders(headers: Map<String, String>): OkHttpClient.Builder {
    this.addInterceptor { chain ->
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        for ((header, value) in headers) {
            requestBuilder.addHeader(header, value)
        }

        chain.proceed(requestBuilder.build())
    }

    return this
}