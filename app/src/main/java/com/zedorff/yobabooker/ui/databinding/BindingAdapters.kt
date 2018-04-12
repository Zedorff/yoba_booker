package com.zedorff.yobabooker.ui.databinding

import android.content.res.TypedArray
import android.databinding.BindingAdapter
import android.support.v7.widget.AppCompatSpinner
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.model.db.entities.TransactionEntity

@BindingAdapter("bind:arrayAdapter")
fun setSpinnerAdapter(spinner: AppCompatSpinner, data: List<String>?) {
    data?.let {
        spinner.adapter = ArrayAdapter<String>(spinner.context,
                android.R.layout.simple_spinner_dropdown_item, it)
    }
}

@BindingAdapter("bind:accountImage")
fun setImageDrawable(view: ImageView, type: Int) {
    val array: TypedArray = view.resources.obtainTypedArray(R.array.account_icons)
    view.setImageResource(array.getResourceId(type, -1))
    array.recycle()
}

@BindingAdapter("android:text")
fun setText(view: TextView, transactions: List<TransactionEntity>) {
    view.text = transactions.map { it.value }.sum().toString()
}