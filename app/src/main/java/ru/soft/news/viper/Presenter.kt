package ru.soft.news.viper

import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

/**
 *   Created by dakishin@gmail.com
 *
 *   Базовый презентер. Выполняет модификацию состояния в liveData
 */
abstract class Presenter<T> {

  val liveData = MutableLiveData<T>()

  val dispatcher = PublishSubject.create<Observable<T>>()

  init {
    Observable
        .switchOnNext(dispatcher)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
          liveData.value = it
        }


  }


}