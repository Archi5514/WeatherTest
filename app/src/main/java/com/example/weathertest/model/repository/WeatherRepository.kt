package com.example.weathertest.model.repository

import com.example.weathertest.model.entity.WeatherEntity

interface WeatherRepository {

    suspend fun getWeather(lat: Double, long: Double, exclude: String): WeatherEntity

}