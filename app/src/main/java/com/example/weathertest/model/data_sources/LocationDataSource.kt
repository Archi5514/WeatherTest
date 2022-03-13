package com.example.weathertest.model.data_sources

import com.example.weathertest.model.entity.CityEntity

interface LocationDataSource {

    fun getCityEntity(): CityEntity?

}