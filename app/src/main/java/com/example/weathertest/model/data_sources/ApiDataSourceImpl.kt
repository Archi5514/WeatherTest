package com.example.weathertest.model.data_sources

import com.example.weathertest.model.api.ApiService
import com.example.weathertest.model.entity.WeatherEntity
import kotlinx.coroutines.Deferred

class ApiDataSourceImpl(private val service: ApiService) : ApiDataSource {

    override fun getWeather(
        lat: Double,
        long: Double,
        appId: String,
        exclude: String
    ): Deferred<WeatherEntity> = service.getWeather(lat, long, appId, exclude)

}