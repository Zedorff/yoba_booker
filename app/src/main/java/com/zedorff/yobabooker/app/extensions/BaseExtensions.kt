package com.zedorff.yobabooker.app.extensions

import android.graphics.Rect
import android.graphics.RectF
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import java.util.*

inline fun <T> Iterable<T>.sumBy(selector: (T) -> Float): Float {
    var sum = 0f
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

fun Float.negate(): Float = this * -1

fun Int.negate(): Int = this * -1

fun Float.half(): Float = this / 2F

fun Int.half() : Int = this / 2

val Int.boolean
    get() = this == 1

fun Double.half(): Double = this / 2.0

val Boolean.int
    get() = if (this) 1 else 0

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

fun <T> LiveData<T>.nonNullObserve(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, androidx.lifecycle.Observer {
        it?.let(observer)
    })
}