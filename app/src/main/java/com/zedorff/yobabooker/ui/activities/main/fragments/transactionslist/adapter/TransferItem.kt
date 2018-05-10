package com.zedorff.yobabooker.ui.activities.main.fragments.transactionslist.adapter

import com.zedorff.yobabooker.model.db.embeded.FullTransaction

class TransferItem(var transactionFrom: FullTransaction, var transactionTo: FullTransaction): TransactionListItem {
    override fun getType() = TransactionListItem.TYPE_TRANSFER
}