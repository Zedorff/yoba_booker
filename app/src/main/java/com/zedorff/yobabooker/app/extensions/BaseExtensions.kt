package com.zedorff.yobabooker.app.extensions

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import java.util.*

inline fun <T> Iterable<T>.sumBy(selector: (T) -> Float): Float {
    var sum: Float = 0f
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

fun Float.negate(): Float = this * -1

fun Calendar.getYear(): Int = this.get(Calendar.YEAR)

fun Calendar.getMonth(): Int = this.get(Calendar.MONTH)

fun Calendar.fromTimeInMillis(millis: Long): Calendar {
    timeInMillis = millis
    return this
}