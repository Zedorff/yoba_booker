package com.zedorff.dragandswiperecycler.viewholder

import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.zedorff.dragandswiperecycler.R
import com.zedorff.dragandswiperecycler.extensions.inflate
import kotlinx.android.synthetic.main.swipeable_background_root.view.*
import kotlinx.android.synthetic.main.swipeable_foreground_root.view.*

open class BaseSwipeableViewHolder<T: ViewDataBinding>(
        @LayoutRes
        private val backgroundRes: Int = R.layout.swipeable_background_default,
        private val parent: ViewGroup,
        var binding: T,
        root: ViewGroup = parent.inflate(R.layout.swipeable_root_layout) as ViewGroup
) : RecyclerView.ViewHolder(root) {

    enum class SwipeState {
        IDLE,
        SWIPING;
    }

    private var backgroundRoot = root.inflate(R.layout.swipeable_background_root)
    private var foregroundRoot = root.inflate(R.layout.swipeable_foreground_root)

    private var backgroundMovable = root.inflate(backgroundRes)

    var background: ViewGroup = backgroundRoot.background_movable_root
    var foreground: View = foregroundRoot

    private var foregroundTopDecoration: View = foreground.swipeable_foreground_top_line
    private var foregroundBottomDecoration: View = foreground.swipeable_foreground_bottom_line

    private var state: SwipeState = SwipeState.IDLE

    init {
        root.addView(backgroundRoot, 0)
        root.addView(foregroundRoot, 1)
        foreground.foreground_container.addView(binding.root)
        background.addView(backgroundMovable)
    }

    fun setState(state: SwipeState) {
        this.state = state
        updateAppearanceForState()
    }

    private fun updateAppearanceForState() {
        when(state) {
            SwipeState.IDLE -> {
                foregroundTopDecoration.visibility = View.GONE
                foregroundBottomDecoration.visibility = View.GONE
            }
            SwipeState.SWIPING -> {
                foregroundTopDecoration.visibility = View.VISIBLE
                foregroundBottomDecoration.visibility = View.VISIBLE
            }
        }
    }
}