package com.example.weathertest.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weathertest.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment())
            .addToBackStack(null)
            .commit()
    }

}