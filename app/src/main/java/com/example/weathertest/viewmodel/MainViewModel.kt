package com.example.weathertest.viewmodel

import com.example.weathertest.model.api.Exclude
import com.example.weathertest.model.entity.HourlyEntity
import com.example.weathertest.model.repository.WeatherRepository
import com.example.weathertest.view.AppState

class MainViewModel(private val weatherRepository: WeatherRepository) :
    BaseViewModel<HourlyEntity>() {

    override fun onViewInit() {
        runAsync {
            mSharedFlow.emit(
                AppState.Success(
                    weatherRepository.getWeatherByCity(
                        "city",
                        Exclude.HOURLY
                    ).hourly
                )
            )
        }
    }

}