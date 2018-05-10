package com.zedorff.dragandswiperecycler.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun <T : ViewGroup> T.inflate(@LayoutRes res: Int): View {
    let {
        val inflater = LayoutInflater.from(this.context)
        return inflater.inflate(res, this, false)
    }
}

fun Int.negate(): Int = this * -1
fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()
fun View.dip(value: Int): Int = context.dip(value)
