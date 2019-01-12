package ru.soft.news.persistence

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query


/**
 *   Created by dakishin@gmail.com
 */
@Dao
interface NewsDao {

  @Query("SELECT * FROM NewsRecord ORDER BY publication_date DESC")
  fun getAll(): List<NewsRecord>

  @Insert()
  fun save(news: List<NewsRecord>)

  @Query("DELETE FROM NewsRecord")
  fun deleteAll()

}