package com.example.weathertest.model.repository

import com.example.weathertest.model.api.APP_ID
import com.example.weathertest.model.data_sources.ApiDataSource

class WeatherRepositoryImpl(private val apiDataSource: ApiDataSource) : WeatherRepository {
    override suspend fun getWeatherByCity(city: String, exclude: String) =
        apiDataSource.getWeather(city, APP_ID, exclude).await()
}