package com.zedorff.dragandswiperecycler.helper

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.zedorff.dragandswiperecycler.extensions.negate
import com.zedorff.dragandswiperecycler.viewholder.BaseSwipeableViewHolder

class SDItemTouchCallback(private var listener: SDHelperListener): ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled() = listener.dragDropEnabled()
    override fun isItemViewSwipeEnabled() = listener.swipeEnabled()

    override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START
        return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView?, holder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        if (holder != null && target != null) {
            listener.onDragged(holder.adapterPosition, target.adapterPosition)
        }
        return true
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.let {
            if (it is BaseSwipeableViewHolder<*>) {
                getDefaultUIUtil().onSelected(it.foreground)
            }
        }
    }

    override fun onChildDrawOver(c: Canvas?, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        viewHolder?.let {
            if (it is BaseSwipeableViewHolder<*>) {
                getDefaultUIUtil().onDraw(c, recyclerView, it.foreground, dX, dY, actionState, isCurrentlyActive)
            }
        }
    }

    override fun onChildDraw(c: Canvas?, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        viewHolder?.let {
            if (it is BaseSwipeableViewHolder<*>) {
                getDefaultUIUtil().onDraw(c, recyclerView, it.foreground, dX, dY, actionState, isCurrentlyActive)
                if (dX < it.background.width.negate()) getDefaultUIUtil().onDraw(c, recyclerView, it.background, dX + it.background.width, dY, actionState, isCurrentlyActive)
                if (dX != 0f) it.setState(BaseSwipeableViewHolder.SwipeState.SWIPING)
            }
        }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        viewHolder?.let {
            if (it is BaseSwipeableViewHolder<*>) {
                listener.onSwiped(viewHolder.adapterPosition)
            }
        }
    }

    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?) {
        listener.onDragDropEnded()
        viewHolder?.let {
            if (it is BaseSwipeableViewHolder<*>) {
                getDefaultUIUtil().clearView(it.foreground)
                getDefaultUIUtil().clearView(it.background)
                it.setState(BaseSwipeableViewHolder.SwipeState.IDLE)
            }
        }
    }
}
