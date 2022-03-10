package com.example.weathertest.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weathertest.model.entity.AppStateEntity
import com.example.weathertest.view.AppState
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseViewModel<D : AppStateEntity> : ViewModel() {

    protected val mSharedFlow = MutableSharedFlow<AppState<D>>(
        replay = 2,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.SUSPEND
    )
    val stateLiveData get() = mSharedFlow.asSharedFlow()

    private val ioCoroutineScope = CoroutineScope(
        Dispatchers.IO + SupervisorJob()
    )

    private val mainCoroutineScope = CoroutineScope(
        Dispatchers.Main + SupervisorJob()
    )

    protected fun runOnMainThread(block: suspend () -> Unit) =
        mainCoroutineScope.launch {
            block()
        }

    protected fun runAsync(block: suspend () -> Unit) =
        ioCoroutineScope.launch {
            block()
        }

    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.coroutineContext.cancelChildren()
    }

    abstract fun onViewInit()

}