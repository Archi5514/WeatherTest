package com.example.weathertest

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*

const val DATE_HOUR_FORMAT = "HH:mm"
const val DATE_DAY_FORMAT = "dd"
const val DATE_WEEKDAY_FORMAT = "EE"

fun <T> Flow<T>.launchWhenStarted(lifecycleScope: LifecycleCoroutineScope, block: (T) -> Unit) {
    lifecycleScope.launchWhenStarted {
        collect { t ->
            block(t)
        }
    }
}

fun Date.formatAsHours(): String = SimpleDateFormat(DATE_HOUR_FORMAT, Locale.getDefault()).format(this)

fun Date.formatAsDays(): String = SimpleDateFormat(DATE_DAY_FORMAT, Locale.getDefault()).format(this)

fun Date.formatAsWeekDays(): String = SimpleDateFormat(DATE_WEEKDAY_FORMAT, Locale.getDefault()).format(this)