package com.zedorff.yobabooker.ui.databinding

import android.content.res.TypedArray
import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.widget.ImageView
import android.widget.TextView
import com.zedorff.yobabooker.R
import org.jetbrains.anko.dip

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

@BindingAdapter("bind:categoryIcon", "bind:categoryColor", requireAll = true)
fun setCategoryDrawable(view: ImageView, icon: String, color: Int) {
    with(view.context) {
        val dips = dip(8)
        val resId = resources.getIdentifier(icon, "drawable", this.packageName)
        val baseCircle = ContextCompat.getDrawable(this, R.drawable.ic_category_base_circle)
        DrawableCompat.setTint(baseCircle!!, color)
        val categoryIcon = ContextCompat.getDrawable(this, resId)
        view.setImageDrawable(LayerDrawable(arrayOf(baseCircle, categoryIcon)).apply {
            setLayerInset(1, dips, dips, dips, dips)
        })
    }
}

@BindingAdapter("bind:categoryTypeIcon", "bind:categoryTypeColor", requireAll = true)
fun setCategoryDrawable(view: ImageView, icon: Drawable, color: Int) {
    with(view.context) {
        val dips = dip(4)
        val baseCircle = ContextCompat.getDrawable(this, R.drawable.ic_category_base_circle)
        DrawableCompat.setTint(baseCircle!!, color)
        view.setImageDrawable(LayerDrawable(arrayOf(baseCircle, icon)).apply {
            setLayerInset(1, dips, dips, dips, dips)
        })
    }
}