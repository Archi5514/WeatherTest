package com.example.weathertest.model.entity

data class AdapterEntity(
    val time: String,
    val temp: String,
    val image: Int,
    var windSpeed: Double = 0.0,
    var humidity: Int = 0
) : AppStateEntity