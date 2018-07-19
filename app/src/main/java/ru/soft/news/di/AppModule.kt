package ru.soft.news.di

import android.arch.persistence.room.Room
import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.soft.news.api.newsClient
import ru.soft.news.interactors.ApiInteractor
import ru.soft.news.interactors.StorageInteractor
import ru.soft.news.interactors.StorageInteractorImpl
import ru.soft.news.persistence.NewsDatabase

class AppModule(val context: Context) {

    fun provideApiInteractor(): ApiInteractor {
        val logLevel = HttpLoggingInterceptor.Level.BODY
        val userAgent = "tinkoff_news_android"
        val httpClient = OkHttpClient().newsClient(hashMapOf(Pair("User-Agent", userAgent)), logLevel)
        return Retrofit.Builder().newsClient(httpClient, "https://api.tinkoff.ru/v1/")
    }


    fun provideStorageInteractor(): StorageInteractor {
        val db = Room.databaseBuilder(context,
                NewsDatabase::class.java, "news_database").build()
        return StorageInteractorImpl(db)
    }


}