package com.zedorff.yobabooker.ui.activities.main.fragments.transactions.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zedorff.yobabooker.databinding.ItemTransactionBinding
import com.zedorff.yobabooker.model.db.entities.TransactionEntity
import com.zedorff.yobabooker.ui.activities.base.fragments.adapter.BaseAdapter

class TransactionsAdapter: BaseAdapter<TransactionsAdapter.ViewHolder, TransactionEntity>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.transaction = items[position]
    }

    override fun compareItems(oldItem: TransactionEntity, newItem: TransactionEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun compareContent(oldItem: TransactionEntity, newItem: TransactionEntity): Boolean {
        return oldItem == newItem
    }

    inner class ViewHolder(var binding: ItemTransactionBinding): RecyclerView.ViewHolder(binding.root)
}