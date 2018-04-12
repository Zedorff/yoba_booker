package com.zedorff.yobabooker.ui.activities.main.fragments.accounts.viewmodel

import android.arch.lifecycle.LiveData
import com.zedorff.yobabooker.model.db.dao.AccountDao
import com.zedorff.yobabooker.model.db.embeded.FullAccount
import com.zedorff.yobabooker.model.repository.YobaRepository
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import javax.inject.Inject

class AccountsViewModel @Inject constructor(var repository: YobaRepository): BaseViewModel() {

    var fullAccounts: LiveData<List<FullAccount>> = repository.getFullAccounts()

    fun getAccounts(): LiveData<List<FullAccount>> = fullAccounts
}