package com.zedorff.yobabooker.ui.activities.main.fragments.accountslist.viewmodel

import android.arch.lifecycle.LiveData
import com.zedorff.yobabooker.model.db.embeded.FullAccount
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import com.zedorff.yobabooker.model.repository.YobaRepository
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import javax.inject.Inject

class AccountsListViewModel @Inject constructor(var repository: YobaRepository): BaseViewModel() {

    private var accountsLiveData: LiveData<List<FullAccount>> = repository.getFullAccounts()

    fun getAccounts(): LiveData<List<FullAccount>> = accountsLiveData

    fun updateAccountsOrder(items: List<FullAccount>) {
        items.forEachIndexed { index, fullAccount ->
            fullAccount.account.order = index
        }
        repository.updateAccounts(items.map { it.account })
    }
}