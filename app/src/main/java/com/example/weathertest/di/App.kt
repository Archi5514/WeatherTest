package com.example.weathertest.di

import android.app.Application
import org.koin.core.context.startKoin

open class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(dataSourceModule, retrofitModule, repositoryModule, viewModelModule)
        }
    }

}