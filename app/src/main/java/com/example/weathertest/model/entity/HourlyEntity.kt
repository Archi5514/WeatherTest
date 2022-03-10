package com.example.weathertest.model.entity

data class HourlyEntity(
    var dt: Long = 0,
    var temp: Double = 0.0,
    var humidity: Int = 0,
    var clouds: Int = 0,
    var windSpeed: Double = 0.0,
) : AppStateEntity