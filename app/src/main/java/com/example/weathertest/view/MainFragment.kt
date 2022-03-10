package com.example.weathertest.view

import com.example.weathertest.R
import com.example.weathertest.databinding.FragmentMainBinding
import com.example.weathertest.model.entity.HourlyEntity
import com.example.weathertest.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment :
    BaseFragment<FragmentMainBinding, HourlyEntity, MainViewModel>(
        R.layout.fragment_main
    ) {

    override val viewModel: MainViewModel by viewModel()

}