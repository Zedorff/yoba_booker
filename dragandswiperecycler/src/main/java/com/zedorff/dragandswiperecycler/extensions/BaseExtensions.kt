package com.zedorff.dragandswiperecycler.extensions

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun <T : ViewGroup> T.inflate(@LayoutRes res: Int): View {
    let {
        val inflater = LayoutInflater.from(this.context)
        return inflater.inflate(res, this, false)
    }
}

fun Int.negate(): Int = this * -1
