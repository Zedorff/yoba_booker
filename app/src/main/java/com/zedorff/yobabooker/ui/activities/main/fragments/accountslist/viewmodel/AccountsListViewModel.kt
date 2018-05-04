package com.zedorff.yobabooker.ui.activities.main.fragments.accountslist.viewmodel

import android.arch.lifecycle.LiveData
import com.zedorff.yobabooker.model.db.embeded.FullAccount
import com.zedorff.yobabooker.model.repository.YobaRepository
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import javax.inject.Inject

class AccountsListViewModel @Inject constructor(var repository: YobaRepository): BaseViewModel() {

    private var accountsLiveData: LiveData<List<FullAccount>> = repository.loadFullAccounts()

    fun getAccounts(): LiveData<List<FullAccount>> = accountsLiveData
}