package com.zedorff.yobabooker.ui.activities.main.fragments.categories.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zedorff.yobabooker.app.listeners.ViewHolderClickListener
import com.zedorff.yobabooker.databinding.ItemCategoryBinding
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import com.zedorff.yobabooker.ui.activities.base.fragments.adapter.BaseAdapter
import org.jetbrains.anko.sdk25.coroutines.onClick

class CategoriesAdapter(val listener: ViewHolderClickListener<CategoryEntity>)
    : BaseAdapter<CategoriesAdapter.ViewHolder, CategoryEntity>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCategoryBinding = ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.category = items[position]
        holder.binding.root.onClick {
            listener.onClick(items[holder.adapterPosition])
        }
    }

    override fun compareItems(oldItem: CategoryEntity, newItem: CategoryEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun compareContent(oldItem: CategoryEntity, newItem: CategoryEntity): Boolean {
        return oldItem == newItem
    }

    inner class ViewHolder(var binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)
}