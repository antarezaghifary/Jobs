package com.reza.jobs.di

import com.reza.jobs.BuildConfig
import com.reza.jobs.data.source.remote.provideApi
import com.reza.jobs.data.source.remote.provideCacheInterceptor
import com.reza.jobs.data.source.remote.provideHttpLoggingInterceptor
import com.reza.jobs.data.source.remote.provideMoshiConverter
import org.koin.dsl.module

val remoteModule = module {
    single { provideCacheInterceptor() }
    single { provideHttpLoggingInterceptor() }
    single { provideMoshiConverter() }
    single {
        provideApi(
            BuildConfig.API_URL,
            get()
        )
    }
}
