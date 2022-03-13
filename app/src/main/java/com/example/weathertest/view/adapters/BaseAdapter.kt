package com.example.weathertest.view.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertest.model.entity.MainViewState

abstract class BaseAdapter<T : BaseAdapter<T>.BaseViewHolder> : RecyclerView.Adapter<T>() {

    var data: MainViewState = MainViewState
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    abstract inner class BaseViewHolder(binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        abstract fun bind(position: Int)

    }

    override fun onBindViewHolder(holder: T, position: Int) {
        holder.bind(position)
    }

}