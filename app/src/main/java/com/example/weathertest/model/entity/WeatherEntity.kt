package com.example.weathertest.model.entity

data class WeatherEntity(
    val hourly: HourlyEntity = HourlyEntity()
) : AppStateEntity