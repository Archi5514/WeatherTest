package com.example.weathertest.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathertest.R
import com.example.weathertest.databinding.FragmentMainBinding
import com.example.weathertest.model.entity.MainViewState
import com.example.weathertest.view.adapters.DailyAdapter
import com.example.weathertest.view.adapters.HourlyAdapter
import com.example.weathertest.view.adapters.OnItemClickListener
import com.example.weathertest.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment :
    BaseFragment<FragmentMainBinding, MainViewState, MainViewModel>(
        R.layout.fragment_main
    ), OnItemClickListener {

    override val viewModel: MainViewModel by viewModel()
    private var hourlyAdapter = HourlyAdapter()
    private var dailyAdapter = DailyAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvHourly.adapter = hourlyAdapter

        binding.rvDaily.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDaily.adapter = dailyAdapter
    }

    override fun renderSuccess(data: MainViewState) {
        dailyAdapter.data = data
        hourlyAdapter.data = data
        super.renderSuccess(data)
    }

    override fun onItemClick(position: Int) {
        viewModel.onDailyItemClick(position)
    }

}