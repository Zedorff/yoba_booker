package com.zedorff.dragandswiperecycler.viewholder

import android.support.v7.widget.helper.ItemTouchHelper

interface DraggableViewHolder: SDViewHolder {
    fun enableDragHandler(touchHelper: ItemTouchHelper)
    fun setShadowEnabled(enabled: Boolean)
}