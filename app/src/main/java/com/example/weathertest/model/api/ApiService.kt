package com.example.weathertest.model.api

import com.example.weathertest.model.entity.WeatherEntity
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/data/2.5/onecall")
    fun getWeather(
        @Query("lat") lat: Double,
        @Query("long") long: Double,
        @Query("appid") appId: String,
        @Query("exclude") exclude: String
    ): Deferred<WeatherEntity>

}