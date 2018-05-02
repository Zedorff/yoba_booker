package com.zedorff.dragandswiperecycler.helper

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.zedorff.dragandswiperecycler.extensions.negate
import com.zedorff.dragandswiperecycler.viewholder.DraggableViewHolder
import com.zedorff.dragandswiperecycler.viewholder.SDState
import com.zedorff.dragandswiperecycler.viewholder.SDViewHolder
import com.zedorff.dragandswiperecycler.viewholder.SwipeableViewHolder

class SDItemTouchCallback(private var listener: SDHelperListener) : ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled() = listener.dragDropEnabled()
    override fun isItemViewSwipeEnabled() = listener.swipeEnabled()

    override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        var swipeFlags = 0
        if (viewHolder is SwipeableViewHolder) {
            if (viewHolder.canSwipeLeft()) {
                swipeFlags += ItemTouchHelper.START
            }
            if (viewHolder.canSwipeRight()) {
                swipeFlags += ItemTouchHelper.END
            }
        }
        return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.let {
            if (it is SDViewHolder) {
                it.setState(SDState.from(actionState))
            }
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun onChildDraw(c: Canvas?, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        if (viewHolder is SDViewHolder) {
            with(viewHolder as SDViewHolder) {
                when {
                    getState() == SDState.SWIPING && (this is SwipeableViewHolder) -> {
                        getForegroundView().translationX = dX
                        if (dX < 0 && canSwipeLeft()) { //Swipe to left
                            updateVisibilityForSwipeDirection(SwipeableViewHolder.SwipeDirection.LEFT)
                            with(getBackgroundActionRightView()) {
                                if (dX < width.negate() && getBackgroundRightActionPositionType() == SwipeableViewHolder.PositionType.FLOAT) {
                                    translationX = dX + width
                                }
                            }
                        } else if (dX > 0 && canSwipeRight()) { //Swipe to right
                            updateVisibilityForSwipeDirection(SwipeableViewHolder.SwipeDirection.RIGHT)
                            with(getBackgroundActionLeftView()) {
                                if (dX > width && getBackgroundLeftActionPositionType() == SwipeableViewHolder.PositionType.FLOAT) {
                                    translationX = dX - width
                                }
                            }
                        }
                    }
                    getState() == SDState.DRAGGING && (this is DraggableViewHolder) -> {
                        getForegroundView().translationY = dY
                    }
                }
            }
        }
    }

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        if (viewHolder != null && target != null) {
            listener.onDragged(viewHolder.adapterPosition, target.adapterPosition)
        }
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        viewHolder?.let {
            if (it is SDViewHolder) {
                listener.onSwiped(viewHolder.adapterPosition)
            }
        }
    }

    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?) {
        listener.onDragDropEnded()
        viewHolder?.let {
            if (it is SDViewHolder) {
                getDefaultUIUtil().clearView(it.getForegroundView())
                it.setState(SDState.IDLE)
            }
            if (it is SwipeableViewHolder) {
                getDefaultUIUtil().clearView(it.getBackgroundView())
            }
        }
    }
}
