package com.zedorff.yobabooker.ui.activities.main.fragments.transactionslist.adapter

interface TransactionListItem {
    companion object {
        const val TYPE_TRANSACTION: Int = 0
        const val TYPE_TRANSFER: Int = 1
    }

    fun getType(): Int
}