package com.zedorff.yobabooker.app.extensions

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.graphics.Rect
import android.graphics.RectF
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.view.View
import java.util.*

inline fun <T> Iterable<T>.sumBy(selector: (T) -> Float): Float {
    var sum: Float = 0f
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

fun Float.negate(): Float = this * -1

fun Float.half(): Float = this / 2F

fun Int.half() : Int = this / 2

fun Double.half(): Double = this / 2.0

fun Calendar.getYear(): Int = this.get(Calendar.YEAR)

fun Calendar.getMonth(): Int = this.get(Calendar.MONTH)

fun Calendar.getActualMonth(): Int = this.get(Calendar.MONTH) + 1

fun Calendar.fromTimeInMillis(millis: Long): Calendar {
    timeInMillis = millis
    return this
}

fun RectF.halfWidth() = this.width() / 2F

fun RectF.halfHeight() = this.height() / 2F

fun Rect.halfWidth() = this.width() / 2F

fun Rect.halfHeight() = this.height() / 2F

fun View.getColor(@ColorRes res: Int): Int {
    this.context?.let {
        return ContextCompat.getColor(it, res)
    } ?: return -0xfff
}