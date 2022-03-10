package com.example.weathertest

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.flow.Flow

fun <T> Flow<T>.launchWhenStarted(lifecycleScope: LifecycleCoroutineScope, block: (T) -> Unit) {
    lifecycleScope.launchWhenStarted {
        collect { t ->
            block(t)
        }
    }
}