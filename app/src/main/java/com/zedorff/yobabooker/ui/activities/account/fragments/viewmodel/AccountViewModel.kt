package com.zedorff.yobabooker.ui.activities.account.fragments.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.zedorff.yobabooker.model.db.entities.AccountEntity
import com.zedorff.yobabooker.model.repository.YobaRepository
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import javax.inject.Inject

class AccountViewModel @Inject constructor(var repository: YobaRepository): BaseViewModel() {

    var accountName: MutableLiveData<String> = MutableLiveData()
    var accountType: MutableLiveData<Int> = MutableLiveData()

    private var accountId: MutableLiveData<String?> = MutableLiveData()
    private var accountLiveData: LiveData<AccountEntity>

    init {
        accountLiveData = Transformations.switchMap(accountId, {
            if (it == null) {
                return@switchMap MutableLiveData<AccountEntity>().apply { value = AccountEntity() }
            } else {
                return@switchMap repository.getAccount(it)
            }
        })
    }

    private lateinit var account: AccountEntity

    fun setAccountId(id: String?) {
        accountId.value = id
    }

    fun getAccount(): LiveData<AccountEntity> {
        return accountLiveData
    }

    fun setAccount(account: AccountEntity) {
        this.account = account
        accountName.value = account.name
        accountType.value = account.type
    }

    suspend fun saveAccount() {
        repository.createOrUpdateAccount(
                AccountEntity(id = account.id, name = accountName.value.toString(), type = accountType.value!!)
        )
    }
}