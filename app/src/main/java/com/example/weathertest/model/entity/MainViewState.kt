package com.example.weathertest.model.entity

data class MainViewState(
    val hourlyList: MutableList<AdapterEntity> = mutableListOf(),
    val dailyList: MutableList<AdapterEntity> = mutableListOf(),
    var date: String? = null
) : AppStateEntity
