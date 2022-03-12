package com.example.weathertest.model.entity

data class WeatherEntity(
    val hourly: Array<HourlyEntity> = arrayOf(HourlyEntity())
) : AppStateEntity {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WeatherEntity

        if (!hourly.contentEquals(other.hourly)) return false

        return true
    }

    override fun hashCode(): Int {
        return hourly.contentHashCode()
    }

}