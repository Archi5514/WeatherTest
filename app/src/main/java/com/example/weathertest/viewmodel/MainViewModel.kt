package com.example.weathertest.viewmodel

import com.example.weathertest.formatAsDays
import com.example.weathertest.formatAsHours
import com.example.weathertest.model.api.Exclude
import com.example.weathertest.model.entity.AdapterData
import com.example.weathertest.model.entity.AdapterEntity
import com.example.weathertest.model.repository.WeatherRepository
import com.example.weathertest.view.AppState
import com.example.weathertest.view.CloudImage
import java.util.*

class MainViewModel(private val weatherRepository: WeatherRepository) :
    BaseViewModel<AdapterData>() {

    override fun onViewInit() {
        runAsync {
            val unAdaptedEntity = weatherRepository.getWeather(33.44, -94.04, Exclude.HOURLY).hourly
            val adaptedEntity = AdapterData()

            val startDate = Date(unAdaptedEntity.first().dt * 1000).formatAsDays().toInt()
            val counter = Date(unAdaptedEntity.last().dt * 1000).formatAsDays().toInt() - startDate
            val minTempArray = IntArray(counter + 1) { 100 }
            val maxTempArray = IntArray(counter + 1) { -100 }
            val cloudnessArray = Array(counter + 1) { mutableListOf(CloudImage.SUNNY_BLACK) }

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

                val index = Date(e.dt * 1000).formatAsDays().toInt() - startDate

                cloudnessArray[index].add(hourlyImage)

                if (minTempArray[index] > e.temp.toInt()) minTempArray[index] = e.temp.toInt()
                if (maxTempArray[index] < e.temp.toInt()) maxTempArray[index] = e.temp.toInt()

            }

            for (i in 0..counter) {
                val dailyImage =
                    if (cloudnessArray[i][cloudnessArray[i].size / 2] <= 50) CloudImage.SUNNY_BLACK
                    else CloudImage.CLOUDY_BLACK

                adaptedEntity.dailyList.add(
                    AdapterEntity(
                        (startDate + i).toString(),
                        "${maxTempArray[i]}°/${minTempArray[i]}°",
                        dailyImage
                    )
                )
            }

            mSharedFlow.emit(
                AppState.Success(adaptedEntity)
            )
        }
    }

}