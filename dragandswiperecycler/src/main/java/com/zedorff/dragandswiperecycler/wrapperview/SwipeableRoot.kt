package com.zedorff.dragandswiperecycler.wrapperview

import android.content.Context
import android.support.annotation.LayoutRes
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.zedorff.dragandswiperecycler.extensions.inflate
import com.zedorff.dragandswiperecycler.viewholder.SwipeableViewHolder
import kotlinx.android.synthetic.main.swipeable_root_layout.view.*

class SwipeableRoot(context: Context?, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    fun setShadowVisibility(visibility: Int) {
        foreground_top_decoration.visibility = visibility
        foreground_bottom_decoration.visibility = visibility
        background_top_decoration.visibility = visibility
        background_bottom_decoration.visibility = visibility
    }

    fun setSwipeDirection(direction: SwipeableViewHolder.SwipeDirection) {
        when(direction) {
            SwipeableViewHolder.SwipeDirection.LEFT -> {
                background_action_movable_right.visibility = View.VISIBLE
                background_action_movable_left.visibility = View.GONE
            }
            SwipeableViewHolder.SwipeDirection.RIGHT -> {
                background_action_movable_right.visibility = View.GONE
                background_action_movable_left.visibility = View.VISIBLE
            }
        }
    }

    fun setLeftAction(@LayoutRes res: Int) {
        background_action_movable_left.addView(background_action_movable_left.inflate(res))
    }

    fun setRightAction(@LayoutRes res: Int) {
        background_action_movable_right.addView(background_action_movable_right.inflate(res))
    }

    fun getSwipeableForeground(): View = swipeable_foreground
    fun getSwipeableBackground(): View = swipeable_background
    fun getSwipeableContainer(): ViewGroup = foreground_container
    fun getLeftActionView(): View = background_action_movable_left
    fun getRightActionView(): View = background_action_movable_right
}