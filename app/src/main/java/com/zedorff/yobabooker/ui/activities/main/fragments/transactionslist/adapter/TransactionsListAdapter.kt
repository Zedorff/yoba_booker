package com.zedorff.yobabooker.ui.activities.main.fragments.transactionslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zedorff.dragandswiperecycler.viewholder.BaseSwipeableViewHolder
import com.zedorff.dragandswiperecycler.viewholder.SwipeableViewHolder
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.databinding.ItemTransactionBinding
import com.zedorff.yobabooker.model.db.embeded.FullTransaction
import com.zedorff.yobabooker.ui.activities.base.fragments.adapter.BaseAdapter
import com.zedorff.yobabooker.ui.activities.transaction.TransactionActivity
import com.zedorff.yobabooker.ui.activities.transfer.TransferActivity
import org.jetbrains.anko.sdk25.coroutines.onClick

//TODO Need to group transfers into one deletable view
//Already in WIP, should be done after transfering to androidx
class TransactionsListAdapter: BaseAdapter<TransactionsListAdapter.ViewHolder, FullTransaction>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTransactionBinding.inflate(inflater, parent, false)
        return ViewHolder(parent, binding)
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

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItem(item: FullTransaction, position: Int) {
        items.add(position, item)
        notifyItemInserted(position)
    }

    inner class ViewHolder(parent: ViewGroup, binding: ItemTransactionBinding)
        : BaseSwipeableViewHolder<ItemTransactionBinding>(parent = parent, binding = binding) {
        init {
            setBackgroundRightActionResId(R.layout.item_transaction_background)
            setBackgroundRightActionPositionType(SwipeableViewHolder.PositionType.FLOAT)
        }
    }
}