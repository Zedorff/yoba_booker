package com.zedorff.yobabooker.ui.activities.main.fragments.transactionslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zedorff.dragandswiperecycler.viewholder.BaseSwipeableViewHolder
import com.zedorff.dragandswiperecycler.viewholder.SwipeableViewHolder
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.extensions.inverseVisibility
import com.zedorff.yobabooker.databinding.ItemTransactionBinding
import com.zedorff.yobabooker.databinding.ItemTransferBinding
import com.zedorff.yobabooker.ui.activities.base.fragments.adapter.BaseAdapter
import com.zedorff.yobabooker.ui.activities.transaction.TransactionActivity
import com.zedorff.yobabooker.ui.activities.transfer.TransferActivity
import org.jetbrains.anko.sdk25.coroutines.onClick

//TODO Need to group transfers into one deletable view
//Already in WIP, should be done after transfering to androidx
class TransactionsListAdapter(var animateRecycler: () -> Unit) : BaseAdapter<RecyclerView.ViewHolder, TransactionListItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TransactionListItem.TYPE_TRANSACTION -> {
                TransactionHolder(parent, ItemTransactionBinding.inflate(inflater, parent, false))
            }
            else -> {
                TransferHolder(parent, ItemTransferBinding.inflate(inflater, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TransactionListItem.TYPE_TRANSACTION -> {
                val item = items[position] as TransactionItem
                with(holder as TransactionHolder) {
                    binding.transaction = item.transaction.transaction
                    binding.category = item.transaction.category
                    binding.account = item.transaction.account
                    binding.root.onClick {
                        TransactionActivity.startEdit(itemView.context,
                                item.transaction.transaction.type,
                                item.transaction.transaction.id)
                    }
                }
            }
            TransactionListItem.TYPE_TRANSFER -> {
                val item = items[position] as TransferItem
                with(holder as TransferHolder) {
                    binding.transferTo = item.transactionTo
                    binding.transferFrom = item.transactionFrom
                    binding.root.onClick {
                        TransferActivity.startEdit(itemView.context, item.transactionFrom.transaction.transferId!!)
                    }
                    binding.transferMoreIcon.setOnClickListener {
                        animateRecycler()
                        it.rotation = (it.rotation + 180F) % 360
                        binding.transferDetails.inverseVisibility()
                        binding.transferDividers.inverseVisibility()
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getType()
    }

    override fun compareItems(oldItem: TransactionListItem, newItem: TransactionListItem): Boolean {
        return oldItem.getType() == newItem.getType()
    }

    override fun compareContent(oldItem: TransactionListItem, newItem: TransactionListItem): Boolean {
        return oldItem.getType() == newItem.getType()
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItem(item: TransactionListItem, position: Int) {
        items.add(position, item)
        notifyItemInserted(position)
    }

    inner class TransactionHolder(parent: ViewGroup, binding: ItemTransactionBinding)
        : BaseSwipeableViewHolder<ItemTransactionBinding>(parent = parent, binding = binding) {
        init {
            setBackgroundRightActionResId(R.layout.item_transaction_background)
            setBackgroundRightActionPositionType(SwipeableViewHolder.PositionType.FLOAT)
        }
    }

    inner class TransferHolder(parent: ViewGroup, binding: ItemTransferBinding)
        : BaseSwipeableViewHolder<ItemTransferBinding>(parent = parent, binding = binding) {
        init {
            setBackgroundRightActionResId(R.layout.item_transaction_background)
            setBackgroundRightActionPositionType(SwipeableViewHolder.PositionType.FLOAT)
        }
    }
}