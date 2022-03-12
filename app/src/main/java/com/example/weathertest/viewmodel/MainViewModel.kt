package com.example.weathertest.viewmodel

import com.example.weathertest.model.api.Exclude
import com.example.weathertest.model.entity.HourlyAdapterData
import com.example.weathertest.model.entity.HourlyAdapterEntity
import com.example.weathertest.model.repository.WeatherRepository
import com.example.weathertest.view.AppState
import com.example.weathertest.view.CloudImage

class MainViewModel(private val weatherRepository: WeatherRepository) :
    BaseViewModel<HourlyAdapterData>() {

    override fun onViewInit() {
        runAsync {
            val unAdaptedEntity = weatherRepository.getWeather(33.44, -94.04, Exclude.HOURLY).hourly
            val adaptedEntity = HourlyAdapterData()

            for (e in unAdaptedEntity) {
                val image  = if (e.clouds <= 50) CloudImage.SUNNY_SMALL
                else CloudImage.CLOUDY_SMALL

                adaptedEntity.list.add(HourlyAdapterEntity(
                    e.dt.toString(),
                    e.temp.toInt().toString(),
                    image
                ))

            }

            mSharedFlow.emit(
                AppState.Success(adaptedEntity)
            )
        }
    }

}