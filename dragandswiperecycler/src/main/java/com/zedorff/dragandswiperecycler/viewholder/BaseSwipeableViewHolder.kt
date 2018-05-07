package com.zedorff.dragandswiperecycler.viewholder

import android.databinding.ViewDataBinding
import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.zedorff.dragandswiperecycler.R
import com.zedorff.dragandswiperecycler.extensions.inflate
import com.zedorff.dragandswiperecycler.wrapperview.SwipeableRoot

open class BaseSwipeableViewHolder<T : ViewDataBinding> (
        @LayoutRes
        private val parent: ViewGroup,
        var binding: T,
        var swipeableRoot: SwipeableRoot = parent.inflate(R.layout.swipeable_root_layout) as SwipeableRoot
        ) : RecyclerView.ViewHolder(swipeableRoot), SwipeableViewHolder {

    private var backgroundActionLeftPositionType = SwipeableViewHolder.PositionType.FIXED
    private var backgroundActionRightPositionType = SwipeableViewHolder.PositionType.FIXED

    private var state: SDState = SDState.IDLE

    private var drawShadow: Boolean = true
    private var canSwipeLeft: Boolean = false
    private var canSwipeRight: Boolean = false

    init {
        swipeableRoot.getSwipeableContainer().addView(binding.root)
    }

    override fun getBackgroundView(): View = swipeableRoot.getSwipeableBackground()
    override fun getForegroundView(): View = swipeableRoot.getSwipeableForeground()
    override fun getBackgroundActionLeftView(): View = swipeableRoot.getLeftActionView()
    override fun getBackgroundActionRightView(): View = swipeableRoot.getRightActionView()

    override fun canSwipeLeft() = canSwipeLeft
    override fun canSwipeRight() = canSwipeRight
    override fun getState() = state

    override fun getBackgroundRightActionPositionType() = backgroundActionRightPositionType
    override fun getBackgroundLeftActionPositionType() = backgroundActionLeftPositionType

    override fun setState(state: SDState) {
        this.state = state
        updateAppearanceForState()
    }

    override fun updateAppearanceForState() {
        when (state) {
            SDState.IDLE -> {
                if (drawShadow) {
                    swipeableRoot.setShadowVisibility(View.INVISIBLE)
                }
            }
            SDState.SWIPING -> {
                if (drawShadow) {
                    swipeableRoot.setShadowVisibility(View.VISIBLE)
                }
            }
            SDState.DRAGGING -> {}
        }
    }

    override fun updateVisibilityForSwipeDirection(direction: SwipeableViewHolder.SwipeDirection) {
        swipeableRoot.setSwipeDirection(direction)
    }

    fun setBackgroundRightActionResId(@LayoutRes layoutId: Int) {
        swipeableRoot.setRightAction(layoutId)
        canSwipeLeft = true
    }

    fun setBackgroundLeftActionResId(@LayoutRes layoutId: Int) {
        swipeableRoot.setLeftAction(layoutId)
        canSwipeRight = true
    }

    fun setBackgroundRightActionPositionType(type: SwipeableViewHolder.PositionType) {
        backgroundActionRightPositionType = type
    }

    fun setBackgroundLeftActionPositionType(type: SwipeableViewHolder.PositionType) {
        backgroundActionLeftPositionType = type
    }

    fun setBackgroundColor(@ColorRes colorRes: Int) {
        swipeableRoot.setBackgroundColor(ContextCompat.getColor(swipeableRoot.context, colorRes))
    }

    fun setDrawShadow(enabled: Boolean) {
        drawShadow = enabled
    }

    override fun onClearView() {}
}
