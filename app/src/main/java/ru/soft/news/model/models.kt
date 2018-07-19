package ru.soft.news.model

/**
 *   Created by dakishin@gmail.com
 */
typealias Millis = Long

data class News(val id: String, val title: String, val publicationDate: Millis)
data class NewsContent(val id: String, val title: String, val content: String)