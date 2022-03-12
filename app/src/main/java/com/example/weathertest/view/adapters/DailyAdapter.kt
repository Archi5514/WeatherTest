package com.example.weathertest.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.weathertest.R
import com.example.weathertest.databinding.RvDailyItemBinding

class DailyAdapter : BaseAdapter<DailyAdapter.DailyViewHolder>() {

    inner class DailyViewHolder(private val binding: RvDailyItemBinding) :
        BaseAdapter<DailyAdapter.DailyViewHolder>.BaseViewHolder(binding) {

        override fun bind(position: Int) {
            binding.dateTextView.text = data.dailyList[position].time
            binding.temperatureTextView.text = data.dailyList[position].temp
            binding.cloudnessImageView.setImageResource(data.dailyList[position].image)
        }
    }

    override fun getItemCount(): Int = data.dailyList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val binding: RvDailyItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_daily_item,
            parent,
            false
        )

        return DailyViewHolder(binding)
    }

}