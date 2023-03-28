package com.reza.jobs.ui.screen.login

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.reza.jobs.ui.base.BaseViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class LoginViewModel (application: Application) :
    BaseViewModel<LoginNavigator>(application) {

    fun successLogin(): Deferred<Boolean> {
        return viewModelScope.async {
            delay(1000)
            navigator?.navigateToHomeJob()
            return@async true
        }
    }

}