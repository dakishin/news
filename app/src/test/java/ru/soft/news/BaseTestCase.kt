package ru.soft.news

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.mockito.MockitoAnnotations


/**
 *   Created by dakishin@gmail.com
 */
abstract class BaseTestCase {


    @Before
    open fun setUp() {
        val trampoline = Schedulers.trampoline()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { trampoline }
        RxAndroidPlugins.setMainThreadSchedulerHandler { trampoline }

        RxJavaPlugins.setInitIoSchedulerHandler { trampoline }
        RxJavaPlugins.setIoSchedulerHandler { trampoline }
        MockitoAnnotations.initMocks(this)

    }


}