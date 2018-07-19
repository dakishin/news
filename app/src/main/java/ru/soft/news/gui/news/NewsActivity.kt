package ru.soft.news.gui.news

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.soft.news.NewsApplication
import ru.soft.news.R
import ru.soft.news.viper.RouterProvider

/**
 * Корневая активити для модуля новостей.
 * Отвечает за навигацию.
 */

class NewsActivity : AppCompatActivity() {

    private lateinit var newsModule: NewsComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        newsModule = ViewModelProviders.of(this).get(NewsComponent::class.java)
        (application as NewsApplication).iocContainer.inject(newsModule)

        val routerProvider = RouterProvider(this)

//      Подписываемся на роутинг активити. Роутинг восстанавливается только здесь.
        newsModule.router.liveRoute.observe(this,
                Observer {
                    it?.let {
                        routerProvider.route(it)
                    }
                })
        if (savedInstanceState == null) {
            //Если идет восстановление, то заново загружать список не нужно.
            newsModule.router.showNewsList()
            newsModule.listPresenter.loadNews()
        }

    }


    override fun onBackPressed() {
//      Определяем вернутся на предыдущий экран или выйти из приложения.
        if (supportFragmentManager.backStackEntryCount == 0) {
            newsModule.router.finish()
        } else {
            newsModule.router.back()
        }
    }
}
