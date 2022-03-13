package com.example.weathertest.model.entity

object MainViewState : AppStateEntity {
    val hourlyList: MutableList<AdapterEntity> = mutableListOf()
    val dailyList: MutableList<AdapterEntity> = mutableListOf()
    var date: String = ""
}