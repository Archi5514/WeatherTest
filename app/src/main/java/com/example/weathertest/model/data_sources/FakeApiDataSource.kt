package com.example.weathertest.model.data_sources

import com.example.weathertest.model.entity.WeatherEntity
import com.example.weathertest.model.entity.HourlyEntity
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class FakeApiDataSource : ApiDataSource {

    override fun getWeather(
        lat: Double,
        long: Double,
        appId: String,
        exclude: String
    ): Deferred<WeatherEntity> = runBlocking {
        async {
            WeatherEntity(listOf(
                HourlyEntity(dt = 1618315200, temp = 12.5),
                HourlyEntity(),
                HourlyEntity(),
                HourlyEntity(),
                HourlyEntity(),
                HourlyEntity(),
                HourlyEntity(),
                HourlyEntity(),
                HourlyEntity(),
                HourlyEntity(),
                HourlyEntity()
            ))
        }
    }

}