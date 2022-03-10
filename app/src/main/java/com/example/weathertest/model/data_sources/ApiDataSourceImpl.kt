package com.example.weathertest.model.data_sources

import com.example.weathertest.model.api.ApiService
import com.example.weathertest.model.entity.WeatherEntity
import kotlinx.coroutines.Deferred

class ApiDataSourceImpl(val service: ApiService) : ApiDataSource {

    override fun getWeather(
        city: String,
        appId: String,
        exclude: String
    ): Deferred<WeatherEntity> = service.getWeather(city, appId, exclude)

}