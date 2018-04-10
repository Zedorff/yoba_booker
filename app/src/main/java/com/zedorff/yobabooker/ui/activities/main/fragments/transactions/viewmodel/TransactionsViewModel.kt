package com.zedorff.yobabooker.ui.activities.main.fragments.transactions.viewmodel

import com.zedorff.yobabooker.model.db.dao.TransactionDao
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import javax.inject.Inject

class TransactionsViewModel @Inject constructor(var transactionsDao: TransactionDao): BaseViewModel() {
    fun getTransactions() = transactionsDao.getTransactions()
}