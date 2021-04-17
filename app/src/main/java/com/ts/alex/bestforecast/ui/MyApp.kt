package com.ts.alex.bestforecast.ui

import android.app.Application
import com.ts.alex.bestforecast.di.singleModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            androidLogger(Level.DEBUG)
            modules(listOf(singleModule))
        }
    }
}