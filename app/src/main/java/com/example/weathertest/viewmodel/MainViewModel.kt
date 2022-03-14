package com.example.weathertest.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import com.example.weathertest.formatAsDays
import com.example.weathertest.formatAsHours
import com.example.weathertest.formatAsWeekDays
import com.example.weathertest.model.api.Exclude
import com.example.weathertest.model.data_sources.LocationDataSource
import com.example.weathertest.model.entity.MainViewState
import com.example.weathertest.model.entity.AdapterEntity
import com.example.weathertest.model.entity.HourlyEntity
import com.example.weathertest.model.mappers.MainMapper
import com.example.weathertest.model.repository.WeatherRepository
import com.example.weathertest.view.AppState
import com.example.weathertest.view.CloudImage
import java.math.RoundingMode
import java.util.*
import kotlin.math.roundToInt


@SuppressLint("MissingPermission")
class MainViewModel(
    private val mapper: MainMapper,
    private val locationDataSource: LocationDataSource
) :
    BaseViewModel<MainViewState>() {

    companion object {
        const val DEFAULT_LAT = 47.49
        const val DEFAULT_LONG = 35.11
        const val DEFAULT_NAME = "Zaporizhya"
    }

    private var adaptedEntity = MainViewState()
    private var cityEntity = locationDataSource.getCityEntity()
    private var day: String? = null

    override fun onViewInit() {
        updateLocation()
    }

    fun getCityName() = cityEntity?.name ?: DEFAULT_NAME

    fun updateLocation() {
        cityEntity = locationDataSource.getCityEntity()
        cityEntity?.let { updateView(it.lat, it.long) } ?: updateView(DEFAULT_LAT, DEFAULT_LONG)
    }

    fun onDailyItemClick(position: Int) {
        runAsync {
            day = adaptedEntity.dailyList[position].time
            adaptedEntity.date = day!!

            mSharedFlow.emit(
                AppState.Success(adaptedEntity)
            )
        }
    }

    private fun updateView(lat: Double, long: Double) {
        runAsync {
            adaptedEntity = mapper.map(lat, long)
            if (day == null) day = adaptedEntity.dailyList.first().time
            adaptedEntity.date = day!!

            mSharedFlow.emit(
                AppState.Success(adaptedEntity)
            )
        }
    }

}