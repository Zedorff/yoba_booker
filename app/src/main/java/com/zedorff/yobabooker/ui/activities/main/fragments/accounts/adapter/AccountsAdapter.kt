package com.zedorff.yobabooker.ui.activities.main.fragments.accounts.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zedorff.yobabooker.databinding.ItemAccountBinding
import com.zedorff.yobabooker.model.db.embeded.FullAccount
import com.zedorff.yobabooker.ui.activities.base.fragments.adapter.BaseAdapter

class AccountsAdapter : BaseAdapter<AccountsAdapter.ViewHolder, FullAccount>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var binding = ItemAccountBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.account = items[position].account
    }

    override fun compareItems(oldItem: FullAccount, newItem: FullAccount): Boolean {
        return oldItem.account.id == newItem.account.id
    }

    override fun compareContent(oldItem: FullAccount, newItem: FullAccount): Boolean {
        return oldItem.account == newItem.account
    }

    inner class ViewHolder(var binding: ItemAccountBinding) : RecyclerView.ViewHolder(binding.root)
}