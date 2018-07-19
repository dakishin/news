package ru.soft.news.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase


/**
 *   Created by dakishin@gmail.com
 */
@Database(entities = arrayOf(NewsRecord::class, NewsContentRecord::class), version = 3)
open abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun newsContentDao(): NewsContentDao
}