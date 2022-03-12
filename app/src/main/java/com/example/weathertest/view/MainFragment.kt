package com.example.weathertest.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathertest.R
import com.example.weathertest.databinding.FragmentMainBinding
import com.example.weathertest.model.entity.HourlyAdapterData
import com.example.weathertest.model.entity.HourlyAdapterEntity
import com.example.weathertest.model.entity.HourlyEntity
import com.example.weathertest.view.adapters.HourlyAdapter
import com.example.weathertest.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment :
    BaseFragment<FragmentMainBinding, HourlyAdapterData, MainViewModel>(
        R.layout.fragment_main
    ) {

    override val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: HourlyAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HourlyAdapter()
        binding.rvHourly.adapter = adapter
    }

    override fun renderSuccess(data: HourlyAdapterData) {
        adapter.data = data
        super.renderSuccess(data)
    }

}