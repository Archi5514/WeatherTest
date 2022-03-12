package com.example.weathertest.di

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.core.context.startKoin

open class App : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            modules(dataSourceModule, retrofitModule, repositoryModule, viewModelModule)
        }
    }

}