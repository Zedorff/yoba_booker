package com.zedorff.dragandswiperecycler.viewholder

import android.view.View

interface SDViewHolder {
    fun getForegroundView(): View
    fun setState(state: SDState)
    fun getState(): SDState
    fun updateAppearanceForState()
    fun onClearView()
}