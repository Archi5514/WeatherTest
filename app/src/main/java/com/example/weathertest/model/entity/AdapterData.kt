package com.example.weathertest.model.entity

data class AdapterData(
    val hourlyList: MutableList<AdapterEntity> = mutableListOf(),
    val dailyList: MutableList<AdapterEntity> = mutableListOf()
) : AppStateEntity