package com.zedorff.yobabooker.ui.activities.main.fragments.categorieslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import com.zedorff.dragandswiperecycler.viewholder.BaseDraggableViewHolder
import com.zedorff.yobabooker.databinding.ItemCategoryBinding
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import com.zedorff.yobabooker.ui.activities.base.fragments.adapter.BaseAdapter
import org.jetbrains.anko.sdk25.coroutines.onClick

class CategoriesListAdapter(var touchHelper: ItemTouchHelper)
    : BaseAdapter<CategoriesListAdapter.ViewHolder, CategoryEntity>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCategoryBinding = ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(parent, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.category = items[position]
        holder.binding.root.onClick {
        }
    }

    override fun compareItems(oldItem: CategoryEntity, newItem: CategoryEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun compareContent(oldItem: CategoryEntity, newItem: CategoryEntity): Boolean {
        return oldItem == newItem
    }

    inner class ViewHolder(parent: ViewGroup, var binding: ItemCategoryBinding)
        : BaseDraggableViewHolder<ItemCategoryBinding>(parent = parent, binding = binding) {
        init {
            enableDragHandler(touchHelper)
        }
    }
}