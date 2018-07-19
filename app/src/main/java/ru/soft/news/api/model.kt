package ru.soft.news.api

/**
 *   Created by dakishin@gmail.com
 */

interface Response {
    val resultCode: String
}

data class PublicationDate(val milliseconds: Long)
data class News(val publicationDate: PublicationDate, val id: String, val text: String)
data class ResponseNewsList(override val resultCode: String,
                            val payload: List<News>) : Response


data class ContentTitle(val text: String, val id: String)
data class NewsContent(val title: ContentTitle, val content: String)
data class ResponseNewsContent(
        override val resultCode: String,
        var payload: NewsContent) : Response

