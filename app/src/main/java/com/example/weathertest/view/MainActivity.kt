package com.example.weathertest.view

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.weathertest.R
import com.example.weathertest.di.App
import com.example.weathertest.model.data_sources.LocationDataSourceImpl
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val locationDataSource: LocationDataSourceImpl by inject()

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        val fragment = MainFragment()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationDataSource.locationActivity = this

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

}