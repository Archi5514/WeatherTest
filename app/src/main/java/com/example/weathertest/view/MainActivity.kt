package com.example.weathertest.view

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.weathertest.R
import com.example.weathertest.di.App
import com.example.weathertest.model.data_sources.LocationDataSourceImpl
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val locationDataSource: LocationDataSourceImpl by inject()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationDataSource.locationActivity = this

        if (supportFragmentManager.fragments == mutableListOf<Fragment>()) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, MainFragment())
                .commit()
        }
    }

}