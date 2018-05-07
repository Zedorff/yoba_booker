package com.zedorff.yobabooker.app.extensions

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.content.Context
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*

inline fun <T> Iterable<T>.sumBy(selector: (T) -> Float): Float {
    var sum: Float = 0f
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

fun Float.negate(): Float = this * -1

fun Int.negate(): Int = this * -1

fun Float.half(): Float = this / 2F

fun Int.half() : Int = this / 2

fun Double.half(): Double = this / 2.0

fun Calendar.getYear(): Int = this.get(Calendar.YEAR)

fun Calendar.getMonth(): Int = this.get(Calendar.MONTH)

fun Calendar.getActualMonth(): Int = this.get(Calendar.MONTH) + 1

fun Calendar.getDayOfMonth(): Int = this.get(Calendar.DAY_OF_MONTH)

fun Calendar.fromTimeInMillis(millis: Long): Calendar {
    timeInMillis = millis
    return this
}

fun RectF.halfWidth() = this.width() / 2F

fun RectF.halfHeight() = this.height() / 2F

fun Rect.halfWidth() = this.width() / 2F

fun Rect.halfHeight() = this.height() / 2F

fun View.getColor(@ColorRes res: Int): Int {
    return ContextCompat.getColor(this.context, res)
}

fun <T : ViewGroup> T.inflate(@LayoutRes res: Int): View {
    let {
        val inflater = LayoutInflater.from(this.context)
        return inflater.inflate(res, this, false)
    }
}

fun <T> List<T>.swap(from: Int, to: Int) {
    if (from < to) {
        for (i in from until to) {
            Collections.swap(this, i, i + 1)
        }
    } else {
        for (i in from downTo to + 1) {
            Collections.swap(this, i, i - 1)
        }
    }
}