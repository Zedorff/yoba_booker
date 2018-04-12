package com.zedorff.yobabooker.ui.activities.newaccount.fragments.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zedorff.yobabooker.model.db.dao.AccountDao
import com.zedorff.yobabooker.model.db.entities.AccountEntity
import com.zedorff.yobabooker.model.repository.YobaRepository
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import javax.inject.Inject

class NewAccountViewModel @Inject constructor(var repository: YobaRepository): BaseViewModel() {

    var accountName: MutableLiveData<String> = MutableLiveData()
    var accountType: MutableLiveData<Int> = MutableLiveData()

    fun saveNewAccount(): Deferred<Long> {
        return repository.createAccount(
                AccountEntity(name = accountName.value.toString(), type = accountType.value!!)
        )
    }
}