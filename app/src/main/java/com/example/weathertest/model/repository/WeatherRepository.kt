package com.example.weathertest.model.repository

import com.example.weathertest.model.entity.WeatherEntity

interface WeatherRepository {

    suspend fun getWeatherByCity(city: String, exclude: String): WeatherEntity

}