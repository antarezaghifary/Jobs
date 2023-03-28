package com.reza.jobs.di

import com.reza.jobs.ui.screen.splash.SplashViewModel
import com.reza.jobs.util.ContextProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(androidApplication()) }
}

val apiRepositoryModule = module {
    single { ContextProvider.getInstance() }
}