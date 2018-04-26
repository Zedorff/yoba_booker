package com.zedorff.yobabooker.ui.activities.base.fragments.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView

abstract class BaseAdapter<VH: RecyclerView.ViewHolder, IT: Any>: RecyclerView.Adapter<VH>() {

    var items: MutableList<IT> = mutableListOf()

    fun swapItems(newItems: List<IT>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(items, newItems))
        this.items = newItems.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = items.size

    protected abstract fun compareItems(oldItem: IT, newItem: IT): Boolean
    protected abstract fun compareContent(oldItem: IT, newItem: IT): Boolean

    inner class DiffCallback(var oldItems: List<IT>, var newItems: List<IT>) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return compareItems(oldItems[oldItemPosition], newItems[newItemPosition])
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return compareContent(oldItems[oldItemPosition], newItems[newItemPosition])
        }

        override fun getOldListSize(): Int = oldItems.size
        override fun getNewListSize(): Int = newItems.size
    }
}