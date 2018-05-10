package com.zedorff.yobabooker.ui.activities.main.fragments.transactionslist.adapter

import com.zedorff.yobabooker.model.db.embeded.FullTransaction

class TransactionItem(var transaction: FullTransaction): TransactionListItem {
    override fun getType() = TransactionListItem.TYPE_TRANSACTION
}