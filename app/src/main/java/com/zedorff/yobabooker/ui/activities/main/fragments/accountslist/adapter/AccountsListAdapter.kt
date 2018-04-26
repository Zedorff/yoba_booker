package com.zedorff.yobabooker.ui.activities.main.fragments.accountslist.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zedorff.yobabooker.app.extensions.sumBy
import com.zedorff.yobabooker.app.listeners.ViewHolderClickListener
import com.zedorff.yobabooker.databinding.ItemAccountBinding
import com.zedorff.yobabooker.model.db.embeded.FullAccount
import com.zedorff.yobabooker.ui.activities.account.AccountActivity
import com.zedorff.yobabooker.ui.activities.base.fragments.adapter.BaseAdapter
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onLongClick

class AccountsListAdapter(var listener: ViewHolderClickListener<FullAccount>) : BaseAdapter<AccountsListAdapter.ViewHolder, FullAccount>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val binding = ItemAccountBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.account = items[position].account
        holder.binding.balance = items[position].transactions.sumBy { it.value }
        holder.binding.root.onClick { listener.onClick(items[holder.adapterPosition]) }
    }

    override fun compareItems(oldItem: FullAccount, newItem: FullAccount): Boolean {
        return oldItem.account.id == newItem.account.id
    }

    override fun compareContent(oldItem: FullAccount, newItem: FullAccount): Boolean {
        return oldItem.account == newItem.account
    }

    inner class ViewHolder(var binding: ItemAccountBinding) : RecyclerView.ViewHolder(binding.root)
}