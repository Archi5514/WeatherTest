package com.example.weathertest.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathertest.R
import com.example.weathertest.databinding.FragmentMainBinding
import com.example.weathertest.formatAsWeekDays
import com.example.weathertest.model.entity.AdapterEntity
import com.example.weathertest.model.entity.MainViewState
import com.example.weathertest.view.adapters.DailyAdapter
import com.example.weathertest.view.adapters.HourlyAdapter
import com.example.weathertest.view.adapters.OnItemClickListener
import com.example.weathertest.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
class MainFragment :
    BaseFragment<FragmentMainBinding, MainViewState, MainViewModel>(
        R.layout.fragment_main
    ), OnItemClickListener {

    override val viewModel: MainViewModel by viewModel()

    private var hourlyAdapter = HourlyAdapter()
    private var dailyAdapter = DailyAdapter(this)

    private val locationPermissionCode = 2

    override fun onStart() {
        super.onStart()
        requestPermissions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvHourly.adapter = hourlyAdapter

        binding.rvDaily.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDaily.adapter = dailyAdapter
        binding.cityTextView.text = viewModel.getCityName()

        binding.imageViewLocation.setOnClickListener {
            binding.cityTextView.text = viewModel.getCityName()
            viewModel.updateLocation()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView(data: AdapterEntity) {
        val img = if (data.image == CloudImage.CLOUDY_BLACK) CloudImage.CLOUDY_BIG
        else CloudImage.SUNNY_BIG

        binding.cloudnessImageView.setImageResource(img)
        binding.dateTextView.text = data.time
        binding.temperatureTextView.text = data.temp
        binding.humidityTextView.text = "${data.humidity}%"
        binding.windSpeedTextView.text = data.windSpeed.toString()
    }

    override fun renderSuccess(data: MainViewState) {
        for (d in data.dailyList) {
            if (d.time == data.date) {
                initView(d)
                break
            }
        }
        dailyAdapter.data = data
        hourlyAdapter.data = data
        super.renderSuccess(data)
    }

    override fun onItemClick(position: Int) {
        viewModel.onDailyItemClick(position)
    }

    private fun requestPermissions() {
        if ((ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        }
    }

}