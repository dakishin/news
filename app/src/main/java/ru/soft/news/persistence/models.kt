package ru.soft.news.persistence

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 *   Created by dakishin@gmail.com
 */
@Entity
data class NewsRecord(
        @PrimaryKey
        var id: String = "",

        @ColumnInfo(name = "title")
        var title: String? = null,

        @ColumnInfo(name = "publication_date")
        var publicationDate: Long? = null
)

@Entity
data class NewsContentRecord(
        @PrimaryKey
        @ColumnInfo(name = "id")
        var id: String = "",

        @ColumnInfo(name = "title")
        var title: String? = null,

        @ColumnInfo(name = "content")
        var content: String? = null

)
