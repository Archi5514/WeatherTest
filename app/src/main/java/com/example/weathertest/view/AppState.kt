package com.example.weathertest.view

import com.example.weathertest.model.entity.AppStateEntity

sealed class AppState<out T : AppStateEntity> {
    data class Success<out T : AppStateEntity>(val data: T) : AppState<T>()
    data class Error<out T : AppStateEntity>(val error: Throwable) : AppState<T>()
}