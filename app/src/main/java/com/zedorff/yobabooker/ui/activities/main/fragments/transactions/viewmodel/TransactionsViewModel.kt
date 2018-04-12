package com.zedorff.yobabooker.ui.activities.main.fragments.transactions.viewmodel

import android.arch.lifecycle.LiveData
import com.zedorff.yobabooker.model.db.dao.TransactionDao
import com.zedorff.yobabooker.model.db.embeded.FullTransaction
import com.zedorff.yobabooker.model.repository.YobaRepository
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import javax.inject.Inject

class TransactionsViewModel @Inject constructor(val repository: YobaRepository): BaseViewModel() {

    fun getTransactions(categoryId: String?, accountId: String?): LiveData<List<FullTransaction>> {
        return when {
            categoryId != null -> repository.getFullTransactionsByCategoryId(categoryId)
            accountId != null -> repository.getFullTransactionsByAccountId(accountId)
            else -> repository.getFullTransactions()
        }
    }
}