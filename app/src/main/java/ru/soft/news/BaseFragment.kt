package ru.soft.news

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

/**
 *   Created by dakishin@gmail.com
 */
open class BaseFragment : Fragment() {


  fun enableBackButton(enabled: Boolean) {
    (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(enabled)
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(enabled)
  }


}