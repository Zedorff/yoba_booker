package com.zedorff.yobabooker.ui.databinding

import android.content.res.TypedArray
import android.databinding.BindingAdapter
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatSpinner
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.zedorff.yobabooker.R

@BindingAdapter("bind:accountImage")
fun setImageDrawable(view: ImageView, type: Int) {
    val array: TypedArray = view.resources.obtainTypedArray(R.array.account_icons)
    view.setImageResource(array.getResourceId(type, -1))
    array.recycle()
}

@BindingAdapter("bind:roundableFloatText")
fun setRoundableText(view: TextView, value: Float) {
    if (value % 1 != 0F) view.text = value.toString()
    else view.text = Math.round(value).toString()
}

@BindingAdapter("bind:currencyColor")
fun setColor(view: TextView, value: Float) {
    view.setTextColor(when {
        value > 0 -> { ContextCompat.getColor(view.context, R.color.positive_currency)}
        value < 0 -> { ContextCompat.getColor(view.context, R.color.negative_currency)}
        else -> { ContextCompat.getColor(view.context, R.color.zero_currency)}
    })
}