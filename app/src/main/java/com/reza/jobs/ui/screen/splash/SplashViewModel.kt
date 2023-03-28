package com.reza.jobs.ui.screen.splash

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.reza.jobs.ui.base.BaseViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class SplashViewModel(application: Application) :
    BaseViewModel<SplashNavigator>(application) {

    fun displaySplashAsync(): Deferred<Boolean> {
        return viewModelScope.async {
            delay(2000)
            navigator?.navigateToHomeJob()
            return@async true
        }
    }
}