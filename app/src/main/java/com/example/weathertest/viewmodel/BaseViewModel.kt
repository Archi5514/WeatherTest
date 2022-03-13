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
        Dispatchers.IO
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        }
    )

    private val mainCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        }
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

    open fun handleError(error: Throwable) {
        error.printStackTrace()
        runAsync {
            mSharedFlow.emit(AppState.Error(error))
        }
    }

    abstract fun onViewInit()

}