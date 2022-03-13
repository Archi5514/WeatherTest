package com.example.weathertest.viewmodel

import com.example.weathertest.formatAsDays
import com.example.weathertest.formatAsHours
import com.example.weathertest.formatAsWeekDays
import com.example.weathertest.model.api.Exclude
import com.example.weathertest.model.entity.MainViewState
import com.example.weathertest.model.entity.AdapterEntity
import com.example.weathertest.model.repository.WeatherRepository
import com.example.weathertest.view.AppState
import com.example.weathertest.view.CloudImage
import java.util.*

class MainViewModel(private val weatherRepository: WeatherRepository) :
    BaseViewModel<MainViewState>() {

    private lateinit var adaptedEntity: MainViewState

    override fun onViewInit() {
        runAsync {
            val unAdaptedEntity = weatherRepository.getWeather(33.44, -94.04, Exclude.HOURLY).hourly

            val startDate = Date(unAdaptedEntity.first().dt * 1000).formatAsDays()
            val counter =
                Date(unAdaptedEntity.last().dt * 1000).formatAsDays().toInt() - startDate.toInt()
            val minTempArray = IntArray(counter + 1) { 100 }
            val maxTempArray = IntArray(counter + 1) { -100 }
            val humidityArray = Array<MutableList<Int>>(counter + 1) { mutableListOf() }
            val windSpeedArray = Array<MutableList<Double>>(counter + 1) { mutableListOf() }
            val cloudnessArray = Array<MutableList<Int>>(counter + 1) { mutableListOf() }

            adaptedEntity = MainViewState

            for (e in unAdaptedEntity) {
                val hourlyImage = if (e.clouds <= 50) CloudImage.SUNNY_SMALL
                else CloudImage.CLOUDY_SMALL

                val date = Date(e.dt * 1000).formatAsHours()

                adaptedEntity.hourlyList.add(
                    AdapterEntity(
                        date,
                        "${e.temp.toInt()}°",
                        hourlyImage
                    )
                )

                val index = Date(e.dt * 1000).formatAsDays().toInt() - startDate.toInt()

                if (minTempArray[index] > e.temp.toInt()) minTempArray[index] = e.temp.toInt()
                if (maxTempArray[index] < e.temp.toInt()) maxTempArray[index] = e.temp.toInt()

                cloudnessArray[index].add(e.clouds)
                humidityArray[index].add(e.humidity)
                windSpeedArray[index].add(e.windSpeed)
            }

            for (i in 0..counter) {
                var dailyImage = CloudImage.SUNNY_BLACK
                var dailyWindSpeed = 0.0
                var dailyHumidity = 0
                var cloudnessSum = 0
                var humiditySum = 0
                var windspeedSum = 0.0
                cloudnessArray[i].forEach { cloudnessSum += it }
                humidityArray[i].forEach { humiditySum += it }
                windSpeedArray[i].forEach { windspeedSum += it }

                if (cloudnessArray[i].size != 0) {
                    dailyImage =
                        if (cloudnessSum / cloudnessArray[i].size <= 50) CloudImage.SUNNY_BLACK
                        else CloudImage.CLOUDY_BLACK
                    dailyWindSpeed = windspeedSum / windSpeedArray[i].size
                    dailyHumidity = humiditySum / humidityArray[i].size
                }

                adaptedEntity.dailyList.add(
                    AdapterEntity(
                        Date(unAdaptedEntity.first().dt + i * 86400000).formatAsWeekDays(),
                        "${maxTempArray[i]}°/${minTempArray[i]}°",
                        dailyImage,
                        dailyWindSpeed,
                        dailyHumidity
                    )
                )
            }

            mSharedFlow.emit(
                AppState.Success(adaptedEntity)
            )
        }
    }

    fun onDailyItemClick(position: Int) {
        runAsync {
            val date = adaptedEntity.dailyList[position].time

            adaptedEntity.date = date
            mSharedFlow.emit(
                AppState.Success(adaptedEntity)
            )
        }
    }

}