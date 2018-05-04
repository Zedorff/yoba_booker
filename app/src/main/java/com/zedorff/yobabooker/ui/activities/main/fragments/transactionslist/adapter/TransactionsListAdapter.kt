package com.zedorff.yobabooker.ui.activities.main.fragments.transactionslist.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zedorff.yobabooker.databinding.ItemTransactionBinding
import com.zedorff.yobabooker.model.db.embeded.FullTransaction
import com.zedorff.yobabooker.ui.activities.base.fragments.adapter.BaseAdapter
import com.zedorff.yobabooker.ui.activities.transaction.TransactionActivity
import com.zedorff.yobabooker.ui.activities.transfer.TransferActivity
import org.jetbrains.anko.sdk25.coroutines.onClick

class TransactionsListAdapter: BaseAdapter<TransactionsListAdapter.ViewHolder, FullTransaction>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.transaction = items[position].transaction
        holder.binding.category = items[position].category
        holder.binding.account = items[position].account
        holder.binding.root.onClick {
            items[position].transaction.transferId?.let {
                TransferActivity.startEdit(holder.itemView.context, it)
            } ?: TransactionActivity.startEdit(holder.itemView.context,
                    items[position].transaction.type,
                    items[position].transaction.id)
        }
    }

    override fun compareItems(oldItem: FullTransaction, newItem: FullTransaction): Boolean {
        return oldItem.transaction.id == newItem.transaction.id
    }

    override fun compareContent(oldItem: FullTransaction, newItem: FullTransaction): Boolean {
        return oldItem.transaction == newItem.transaction
    }

    inner class ViewHolder(var binding: ItemTransactionBinding): RecyclerView.ViewHolder(binding.root)
}