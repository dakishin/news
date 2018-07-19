package ru.soft.news

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import ru.soft.news.di.IocContainer

/**
 *   Created by dakishin@gmail.com
 */
class NewsApplication : Application() {

    lateinit var iocContainer: IocContainer

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)

        val iocContainer = IocContainer()
        iocContainer.init(applicationContext)
        this.iocContainer = iocContainer

    }

}