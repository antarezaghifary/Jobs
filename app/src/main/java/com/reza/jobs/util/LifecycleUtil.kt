package com.reza.jobs.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData

fun <T> AppCompatActivity.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, { action.invoke(it) })
}


fun <T> FragmentActivity.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, { action.invoke(it) })
}