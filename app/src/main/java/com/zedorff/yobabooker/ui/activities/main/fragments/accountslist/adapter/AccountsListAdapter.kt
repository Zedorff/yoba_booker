package com.zedorff.yobabooker.ui.activities.main.fragments.accountslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zedorff.dragandswiperecycler.viewholder.BaseDraggableViewHolder
import com.zedorff.yobabooker.app.extensions.sumBy
import com.zedorff.yobabooker.databinding.ItemAccountBinding
import com.zedorff.yobabooker.model.db.embeded.FullAccount
import com.zedorff.yobabooker.ui.activities.account.AccountActivity
import com.zedorff.yobabooker.ui.activities.base.fragments.adapter.BaseAdapter
import org.jetbrains.anko.sdk25.coroutines.onClick

class AccountsListAdapter : BaseAdapter<AccountsListAdapter.ViewHolder, FullAccount>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(parent, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.account = items[position].account
        holder.binding.balance = items[position].transactions.sumBy { it.value }
        holder.binding.root.onClick {
            AccountActivity.build(holder.itemView.context, items[holder.adapterPosition].account.id.toString())
        }
    }

    override fun compareItems(oldItem: FullAccount, newItem: FullAccount): Boolean {
        return oldItem.account.id == newItem.account.id
    }

    override fun compareContent(oldItem: FullAccount, newItem: FullAccount): Boolean {
        return oldItem.account == newItem.account
    }

    inner class ViewHolder(parent: ViewGroup, var binding: ItemAccountBinding)
        : BaseDraggableViewHolder<ItemAccountBinding>(parent = parent, binding = binding)
}