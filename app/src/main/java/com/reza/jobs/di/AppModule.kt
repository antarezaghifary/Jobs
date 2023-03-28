package com.reza.jobs.di

import com.reza.jobs.data.source.repository.JobRepository
import com.reza.jobs.ui.screen.home.HomeJobViewModel
import com.reza.jobs.ui.screen.login.LoginViewModel
import com.reza.jobs.ui.screen.splash.SplashViewModel
import com.reza.jobs.util.ContextProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(androidApplication()) }
    viewModel { LoginViewModel(androidApplication()) }
    viewModel { HomeJobViewModel(get(),androidApplication()) }
}

val apiRepositoryModule = module {
    single { ContextProvider.getInstance() }
    single {
        JobRepository(get())
    }
}