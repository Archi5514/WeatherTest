package com.example.weathertest.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertest.R
import com.example.weathertest.databinding.RvHourlyItemBinding
import com.example.weathertest.model.entity.HourlyAdapterData

class HourlyAdapter : RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {

    var data: HourlyAdapterData = HourlyAdapterData()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class HourlyViewHolder(private val binding: RvHourlyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.timeTextView.text = data.list[position].time
            binding.temperatureTextView.text = data.list[position].temp
            binding.cloudnessImageView.setImageResource(data.list[position].image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val binding: RvHourlyItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_hourly_item,
            parent,
            false
        )

        return HourlyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = data.list.size

}