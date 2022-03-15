package com.example.weathertest.viewmodel

import android.annotation.SuppressLint
import com.example.weathertest.formatAsWeekDays
import com.example.weathertest.model.data_sources.LocationDataSource
import com.example.weathertest.model.entity.MainViewState
import com.example.weathertest.model.mappers.MainMapper
import com.example.weathertest.view.AppState
import java.util.*


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
        initLocation()
    }

    fun getCityName() = cityEntity?.name ?: DEFAULT_NAME

    fun initLocation() {
        cityEntity = locationDataSource.getCityEntity()
        cityEntity?.let { initView(it.lat, it.long) } ?: initView(DEFAULT_LAT, DEFAULT_LONG)
    }

    private fun updateLocation() {
        cityEntity = locationDataSource.getCityEntity()
        cityEntity?.let { updateView(it.lat, it.long) } ?: initView(DEFAULT_LAT, DEFAULT_LONG)
    }

    fun onDailyItemClick(position: Int) {
        runAsync {
            day = adaptedEntity.dailyList[position].time
            adaptedEntity.date = day!!
            updateLocation()

            mSharedFlow.emit(
                AppState.Success(adaptedEntity)
            )
        }
    }

    private fun updateView(lat: Double, long: Double) {
        runAsync {
            if (day == null) day = Date(System.currentTimeMillis()).formatAsWeekDays()
            //to load smoothly
            runOnMainThread { Thread.sleep(200) }
            adaptedEntity.hourlyList.clear()

            mapper.divideHoursToDays(lat, long).forEach { list ->
                if (Date(list.first().dt * 1000).formatAsWeekDays() == day) {
                    list.forEach {
                        adaptedEntity.hourlyList.add(mapper.adaptEntity(it))
                    }
                }
            }

            adaptedEntity.date = day!!

            mSharedFlow.emit(
                AppState.Success(adaptedEntity)
            )
        }
    }

    private fun initView(lat: Double, long: Double) {
        runAsync {
            if (day == null) day = Date(System.currentTimeMillis()).formatAsWeekDays()
            adaptedEntity.hourlyList.clear()
            adaptedEntity.dailyList.clear()

            mapper.divideHoursToDays(lat, long).forEach { list ->
                if (Date(list.first().dt * 1000).formatAsWeekDays() == day) {
                    list.forEach {
                        adaptedEntity.hourlyList.add(mapper.adaptEntity(it))
                    }
                }

                adaptedEntity.dailyList.add(mapper.createDayFromHours(list))
            }

            adaptedEntity.date = day!!

            mSharedFlow.emit(
                AppState.Success(adaptedEntity)
            )
        }
    }

}