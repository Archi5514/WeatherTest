package com.example.weathertest.model.entity

data class HourlyAdapterEntity(
    val time: String,
    val temp: String,
    val image: Int
) : AppStateEntity