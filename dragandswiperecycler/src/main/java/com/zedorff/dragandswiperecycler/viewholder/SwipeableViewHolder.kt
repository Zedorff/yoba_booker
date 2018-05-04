package com.zedorff.dragandswiperecycler.viewholder

import android.view.View

interface SwipeableViewHolder: SDViewHolder {

    enum class PositionType {
        FIXED,
        FLOAT;
    }

    enum class SwipeDirection {
        LEFT,
        RIGHT
    }

    fun getBackgroundView(): View
    fun getBackgroundActionLeftView(): View
    fun getBackgroundActionRightView(): View
    fun getBackgroundRightActionPositionType(): PositionType
    fun getBackgroundLeftActionPositionType(): PositionType
    fun canSwipeLeft(): Boolean
    fun canSwipeRight(): Boolean
    fun updateVisibilityForSwipeDirection(direction: SwipeDirection)
}