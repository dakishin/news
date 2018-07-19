package ru.soft.news

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.soft.news.persistence.*


class DataBaseTest {

    private lateinit var newsDao: NewsDao
    lateinit var database: NewsDatabase
    lateinit var newsContentDao: NewsContentDao

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, NewsDatabase::class.java).build()
        newsDao = database.newsDao()
        newsContentDao = database.newsContentDao()
    }


    @Test
    fun testSaveNews() {
        val newsList = listOf(NewsRecord("id", "title", 0))
        newsDao.save(newsList)
        Assert.assertEquals(newsList[0], newsDao.getAll()[0])

        newsDao.deleteAll()
        Assert.assertEquals(0, newsDao.getAll().size)
    }


    @Test
    fun testSaveNewContent() {
        val newsList = listOf(NewsRecord("id", "title", 0))
        newsDao.save(newsList)
        val news = newsDao.getAll()[0]
        Assert.assertEquals(newsList[0], news)

        val content = NewsContentRecord(news.id, "title", "content")
        newsContentDao.save(content)
        Assert.assertEquals(content, newsContentDao.getByNewsId(news.id))

        newsContentDao.deleteAll()
        Assert.assertNull(newsContentDao.getByNewsId(news.id))

    }


}
