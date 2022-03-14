package com.example.weathertest.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(dataSourceModule, retrofitModule, repositoryModule, viewModelModule, mapperModule)
        }
    }

}