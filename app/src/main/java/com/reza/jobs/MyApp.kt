package com.reza.jobs

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.reza.jobs.di.apiRepositoryModule
import com.reza.jobs.di.remoteModule
import com.reza.jobs.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp : Application() {
    init {
        instance = this
    }
    companion object {
        private var instance: MyApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApp)
            modules(
                listOf(
                    viewModelModule,
                    apiRepositoryModule,
                    remoteModule
                )
            )
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}