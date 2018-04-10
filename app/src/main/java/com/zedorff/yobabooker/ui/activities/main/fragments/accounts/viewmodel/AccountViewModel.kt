package com.zedorff.yobabooker.ui.activities.main.fragments.accounts.viewmodel

import android.arch.lifecycle.LiveData
import com.zedorff.yobabooker.model.db.dao.AccountDao
import com.zedorff.yobabooker.model.db.embeded.FullAccount
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import javax.inject.Inject

class AccountViewModel @Inject constructor(private var accountDao: AccountDao) : BaseViewModel() {
    fun getAccounts(): LiveData<List<FullAccount>> = accountDao.getFullAccounts()
}