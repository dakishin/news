package ru.soft.news.persistence

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


/**
 *   Created by dakishin@gmail.com
 */
@Dao
interface NewsContentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(content: NewsContentRecord)

    @Query("DELETE FROM NewsContentRecord")
    fun deleteAll()

    @Query("SELECT * FROM NewsContentRecord WHERE id =:id")
    fun getByNewsId(id: String): NewsContentRecord?

}