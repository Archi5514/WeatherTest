package com.example.weathertest.model.mappers

import com.example.weathertest.formatAsDays
import com.example.weathertest.formatAsHours
import com.example.weathertest.formatAsWeekDays
import com.example.weathertest.model.api.Exclude
import com.example.weathertest.model.entity.AdapterEntity
import com.example.weathertest.model.entity.HourlyEntity
import com.example.weathertest.model.entity.MainViewState
import com.example.weathertest.model.repository.WeatherRepository
import com.example.weathertest.view.CloudImage
import java.math.RoundingMode
import java.util.*

class MainMapper(private val repository: WeatherRepository) {

    fun adaptEntity(unAdaptedEntity: HourlyEntity): AdapterEntity {
        val hourlyImage = if (unAdaptedEntity.clouds <= 50) CloudImage.SUNNY_SMALL
        else CloudImage.CLOUDY_SMALL

        return AdapterEntity(
            Date(unAdaptedEntity.dt * 1000).formatAsHours(),
            "${unAdaptedEntity.temp.toInt() - 273}°",
            hourlyImage
        )
    }

    suspend fun divideHoursToDays(lat: Double, long: Double): List<List<HourlyEntity>> {
        val day = Date(System.currentTimeMillis()).formatAsDays()

        val daysList = mutableListOf<MutableList<HourlyEntity>>()

        val hoursList = repository.getWeather(
            lat,
            long,
            Exclude.DAILY
        ).hourly

        for (e in hoursList) {
            if (Date(e.dt * 1000).formatAsDays() - day + 1 > daysList.size) daysList.add(
                mutableListOf()
            )
            daysList[Date(e.dt * 1000).formatAsDays() - day].add(e)
        }

        return daysList
    }

    fun createDayFromHours(hoursList: List<HourlyEntity>): AdapterEntity {
        var minTemp = 1000
        var maxTemp = -1000
        val humidityArray = mutableListOf<Int>()
        val windSpeedArray = mutableListOf<Double>()
        val cloudnessArray = mutableListOf<Int>()

        for (e in hoursList) {
            if (minTemp > e.temp.toInt()) minTemp = e.temp.toInt()
            if (maxTemp < e.temp.toInt()) maxTemp = e.temp.toInt()

            cloudnessArray.add(e.clouds)
            humidityArray.add(e.humidity)
            windSpeedArray.add(e.windSpeed)
        }

        val dailyImage =
            if (cloudnessArray.sum() / cloudnessArray.size <= 50) CloudImage.SUNNY_BLACK
            else CloudImage.CLOUDY_BLACK
        val dailyWindSpeed = (windSpeedArray.sum() / windSpeedArray.size).toBigDecimal()
            .setScale(2, RoundingMode.HALF_EVEN).toDouble()
        val dailyHumidity = (humidityArray.sum() / humidityArray.size)

        return AdapterEntity(
            Date(hoursList.first().dt * 1000).formatAsWeekDays(),
            "${maxTemp - 273}°/${minTemp - 273}°",
            dailyImage,
            dailyWindSpeed,
            dailyHumidity
        )
    }

//    suspend fun map(lat: Double, long: Double, day: String): MainViewState {
//        val hoursDividedByDays = divideHoursToDays(lat, long)
//        val daysList = mutableListOf<AdapterEntity>()
//
//        hoursDividedByDays.forEach {
//            daysList.add(createDayFromHours(it))
//        }
//
//        val hoursList = mutableListOf<AdapterEntity>()
//
//        hoursDividedByDays.forEach { list ->
//            if (Date(list.first().dt).formatAsWeekDays() == day) {
//                list.forEach {
//                    hoursList.add(adaptEntity(it))
//                }
//            }
//        }
//
//        return MainViewState(hoursList, daysList, day)
//    }

//    suspend fun map(lat: Double, long: Double): List<AdapterEntity> {
//        val listOfDays = mutableListOf<AdapterEntity>()
//        val unAdaptedEntity = repository.getWeather(
//            lat,
//            long,
//            Exclude.DAILY
//        ).hourly
//        val startDate = Date(unAdaptedEntity.first().dt * 1000).formatAsDays()
//        val counter =
//            Date(unAdaptedEntity.last().dt * 1000).formatAsDays().toInt() - startDate.toInt()
//        val minTempArray = IntArray(counter + 1) { 1000 }
//        val maxTempArray = IntArray(counter + 1) { -1000 }
//        val humidityArray = Array<MutableList<Int>>(counter + 1) { mutableListOf() }
//        val windSpeedArray = Array<MutableList<Double>>(counter + 1) { mutableListOf() }
//        val cloudnessArray = Array<MutableList<Int>>(counter + 1) { mutableListOf() }
//
//        for (e in unAdaptedEntity) {
//
//            val hourlyImage = if (e.clouds <= 50) CloudImage.SUNNY_SMALL
//            else CloudImage.CLOUDY_SMALL
//
//            val date = Date(e.dt * 1000).formatAsHours()
//
//            adaptedEntity.hourlyList.add(
//                AdapterEntity(
//                    date,
//                    "${e.temp.toInt() - 273}°",
//                    hourlyImage
//                )
//            )
//
//            val index = Date(e.dt * 1000).formatAsDays().toInt() - startDate.toInt()
//
//            if (minTempArray[index] > e.temp.toInt()) minTempArray[index] = e.temp.toInt()
//            if (maxTempArray[index] < e.temp.toInt()) maxTempArray[index] = e.temp.toInt()
//
//            cloudnessArray[index].add(e.clouds)
//            humidityArray[index].add(e.humidity)
//            windSpeedArray[index].add(e.windSpeed)
//        }
//
//        for (i in 0..counter) {
//            var dailyImage: Int
//            var dailyWindSpeed: Double
//            var dailyHumidity: Int
//            var cloudnessSum = 0
//            var humiditySum = 0
//            var windspeedSum = 0.0
//            cloudnessArray[i].forEach { cloudnessSum += it }
//            humidityArray[i].forEach { humiditySum += it }
//            windSpeedArray[i].forEach { windspeedSum += it }
//
//            dailyImage =
//                if (cloudnessSum / cloudnessArray[i].size <= 50) CloudImage.SUNNY_BLACK
//                else CloudImage.CLOUDY_BLACK
//            dailyWindSpeed = (windspeedSum / windSpeedArray[i].size).toBigDecimal()
//                .setScale(2, RoundingMode.HALF_EVEN).toDouble()
//            dailyHumidity = (humiditySum / humidityArray[i].size)
//
//            adaptedEntity.dailyList.add(
//                AdapterEntity(
//                    Date(unAdaptedEntity.first().dt + i * 86400000).formatAsWeekDays(),
//                    "${maxTempArray[i] - 273}°/${minTempArray[i] - 273}°",
//                    dailyImage,
//                    dailyWindSpeed,
//                    dailyHumidity
//                )
//            )
//        }
//        return adaptedEntity
//    }

}