package com.example.weathertest.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.weathertest.R
import com.example.weathertest.databinding.RvHourlyItemBinding

class HourlyAdapter : BaseAdapter<HourlyAdapter.HourlyViewHolder>() {

    inner class HourlyViewHolder(private val binding: RvHourlyItemBinding) :
        BaseAdapter<HourlyAdapter.HourlyViewHolder>.BaseViewHolder(binding) {

        override fun bind(position: Int) {
            binding.timeTextView.text = data.hourlyList[position].time
            binding.temperatureTextView.text = data.hourlyList[position].temp
            binding.cloudnessImageView.setImageResource(data.hourlyList[position].image)
        }
    }

    override fun getItemCount(): Int = data.hourlyList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val binding: RvHourlyItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_hourly_item,
            parent,
            false
        )

        return HourlyViewHolder(binding)
    }

}