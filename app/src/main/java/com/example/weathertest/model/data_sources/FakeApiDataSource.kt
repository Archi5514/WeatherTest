package com.example.weathertest.model.data_sources

import com.example.weathertest.model.entity.WeatherEntity
import com.example.weathertest.model.entity.HourlyEntity
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class FakeApiDataSource : ApiDataSource {

    override fun getWeather(
        city: String,
        appId: String,
        exclude: String
    ): Deferred<WeatherEntity> = runBlocking {
        async {
            WeatherEntity(HourlyEntity())
        }
    }

}