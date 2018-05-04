package com.zedorff.yobabooker.ui.activities.main.fragments.transactionslist.viewmodel

import android.arch.lifecycle.LiveData
import com.zedorff.yobabooker.model.db.embeded.FullTransaction
import com.zedorff.yobabooker.model.repository.YobaRepository
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import javax.inject.Inject

class TransactionsListViewModel @Inject constructor(val repository: YobaRepository): BaseViewModel() {

    private var fullTransactions = repository.loadFullTransactions()

    fun getTransactions(): LiveData<List<FullTransaction>> {
        return fullTransactions
    }
}