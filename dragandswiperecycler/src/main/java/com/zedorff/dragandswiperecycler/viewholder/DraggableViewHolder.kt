package com.zedorff.dragandswiperecycler.viewholder

import androidx.recyclerview.widget.ItemTouchHelper

interface DraggableViewHolder: SDViewHolder {
    fun enableDragHandler(touchHelper: ItemTouchHelper)
    fun setShadowEnabled(enabled: Boolean)
}