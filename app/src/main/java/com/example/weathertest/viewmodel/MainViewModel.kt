package com.example.weathertest.viewmodel

import com.example.weathertest.model.api.Exclude
import com.example.weathertest.model.entity.AdapterData
import com.example.weathertest.model.entity.AdapterEntity
import com.example.weathertest.model.repository.WeatherRepository
import com.example.weathertest.view.AppState
import com.example.weathertest.view.CloudImage

class MainViewModel(private val weatherRepository: WeatherRepository) :
    BaseViewModel<AdapterData>() {

    override fun onViewInit() {
        runAsync {
            val unAdaptedEntity = weatherRepository.getWeather(33.44, -94.04, Exclude.HOURLY).hourly
            val adaptedEntity = AdapterData()

            for (e in unAdaptedEntity) {
                val hourlyImage = if (e.clouds <= 50) CloudImage.SUNNY_SMALL
                else CloudImage.CLOUDY_SMALL

                adaptedEntity.hourlyList.add(
                    AdapterEntity(
                        e.dt.toString(),
                        e.temp.toInt().toString(),
                        hourlyImage
                    )
                )

            }

            val dailyImage = CloudImage.SUNNY_BLACK

            adaptedEntity.dailyList.add(
                AdapterEntity(
                    "e.dt.toString()",
                    "e.temp.toInt().toString()",
                    dailyImage
                )
            )

            mSharedFlow.emit(
                AppState.Success(adaptedEntity)
            )
        }
    }

}