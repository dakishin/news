package ru.soft.news.gui.news

import android.Manifest.permission
import android.arch.lifecycle.Observer
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
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
  private val MY_PERMISSIONS_REQUEST_WRIRE_SD_CARD = 100

  lateinit var router: Router

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(findViewById(R.id.toolbar))
    router = NewsApplication.appComponent().router

    val routerProvider = RouterProvider(this)

    router.liveRoute.observe(this,
        Observer {
          it?.let {
            routerProvider.route(it)
          }
        })

    if (savedInstanceState == null) {
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


  override fun onStart() {
    super.onStart()
    requestWriteSdCardPermission()
  }

  private fun requestWriteSdCardPermission() {
    if (ContextCompat.checkSelfPermission(this,
            permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

      // Permission is not granted
      // Should we show an explanation?
      if (ActivityCompat.shouldShowRequestPermissionRationale(this,
              permission.READ_CONTACTS)) {
        // Show an explanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.
      } else {
        // No explanation needed; request the permission
        ActivityCompat.requestPermissions(this,
            arrayOf(permission.WRITE_EXTERNAL_STORAGE),
            MY_PERMISSIONS_REQUEST_WRIRE_SD_CARD)

        // MY_PERMISSIONS_REQUEST_WRIRE_SD_CARD is an
        // app-defined int constant. The callback method gets the
        // result of the request.
      }
    } else {
      // Permission has already been granted
    }
  }
}
