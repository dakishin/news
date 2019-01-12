package ru.soft.news.gui.news

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import ru.soft.news.NewsApplication
import ru.soft.news.R
import ru.soft.news.viper.RouterProvider

/**
 * Корневая активити для модуля новостей.
 * Отвечает за навигацию.
 */
const val TAG = "NewsTAG"

class NewsActivity : AppCompatActivity() {

  lateinit var router: Router

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(findViewById(R.id.toolbar))
    router = NewsApplication.appComponent().router

    val routerProvider = RouterProvider(this)

//      Подписываемся на роутинг активити. Роутинг восстанавливается только здесь.
    router.liveRoute.observe(this,
        Observer {
          it?.let {
            routerProvider.route(it)
          }
        })
    if (savedInstanceState == null) {
      //Если идет восстановление, то заново загружать список не нужно.
      router.showNewsList()
    }

    Log.d(TAG, "activity created")
  }


  override fun onBackPressed() {
//      Определяем вернутся на предыдущий экран или выйти из приложения.
    if (supportFragmentManager.backStackEntryCount == 0) {
      router.finish()
    } else {
      router.back()
    }
  }

  override fun onDestroy() {
    Log.d(TAG, "Activity destroyed")
    super.onDestroy()
  }
}
