package com.example.weathertest.model.entity

data class WeatherEntity(
    val hourly: List<HourlyEntity> = listOf(HourlyEntity())
) : AppStateEntity