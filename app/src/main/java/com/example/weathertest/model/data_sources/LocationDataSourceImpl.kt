package com.example.weathertest.model.data_sources

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.weathertest.model.entity.CityEntity
import java.util.*

@RequiresApi(Build.VERSION_CODES.M)
class LocationDataSourceImpl : LocationDataSource {

    var locationActivity: AppCompatActivity? = null

    override fun getCityEntity(): CityEntity? {
        var lat: Double? = null
        var long: Double? = null
        var name: String? = null
        if (locationActivity?.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            val locationManager =
                locationActivity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

            location?.let {
                name = getCityByCoordinates(location.latitude, location.longitude)
                lat = location.latitude
                long = location.longitude
            }

            if (lat == null || long == null || name == null) return null
            return CityEntity(lat!!, long!!, name!!)

        } else {
            requestLocationPermission()
            return null
        }
    }

    private fun requestLocationPermission() {
        locationActivity?.requestPermissions(
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            228
        )
    }

    private fun getCityByCoordinates(lat: Double, lon: Double): String? {
        val geocoder = Geocoder(locationActivity, Locale.getDefault())
        val addresses = geocoder.getFromLocation(lat, lon, 1)
        if(addresses.isEmpty()) return null
        return addresses[0].locality
    }
}