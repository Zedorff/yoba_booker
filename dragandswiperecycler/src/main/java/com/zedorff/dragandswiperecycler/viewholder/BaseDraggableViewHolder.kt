package com.zedorff.dragandswiperecycler.viewholder

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.view.ViewGroup
import com.zedorff.dragandswiperecycler.R
import com.zedorff.dragandswiperecycler.extensions.inflate
import com.zedorff.dragandswiperecycler.wrapperview.DraggableRoot
import kotlinx.android.synthetic.main.draggable_root_layout.view.*

open class BaseDraggableViewHolder<T : ViewDataBinding>(
        binding: T,
        private val parent: ViewGroup,
        var draggableRoot: DraggableRoot = parent.inflate(R.layout.draggable_root_layout) as DraggableRoot
        ) : RecyclerView.ViewHolder(draggableRoot), DraggableViewHolder {

    private val foreground: ViewGroup = draggableRoot.draggable_content_container
    private var state: SDState = SDState.IDLE
    private var useShadow: Boolean = true

    init {
        foreground.addView(binding.root)
    }

    override fun getForegroundView() = draggableRoot
    override fun getState() = state

    override fun enableDragHandler(touchHelper: ItemTouchHelper) {
        draggableRoot.enableDragHandler()
        draggableRoot.setDragHandlerTouchListener(View.OnTouchListener { _, _ ->
            touchHelper.startDrag(this)
            return@OnTouchListener true
        })
    }

    override fun setShadowEnabled(enabled: Boolean) {
        useShadow = enabled
    }

    override fun setState(state: SDState) {
        this.state = state
        updateAppearanceForState()
    }

    override fun updateAppearanceForState() {
        when (state) {
            SDState.IDLE -> {
                draggableRoot.setElevationDip(0)
            }
            SDState.DRAGGING -> {
                if (useShadow)
                    draggableRoot.setElevationDip(8)
            }
            SDState.SWIPING -> {
            }
        }
    }

    override fun onClearView() {
        draggableRoot.setDragHandlerTouchListener(null)
        draggableRoot.setElevationDip(0)
    }
}