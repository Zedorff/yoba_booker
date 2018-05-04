package com.zedorff.yobabooker.ui.activities.account.fragments.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.databinding.ObservableField
import android.databinding.ObservableInt
import com.zedorff.yobabooker.model.db.entities.AccountEntity
import com.zedorff.yobabooker.model.repository.YobaRepository
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import javax.inject.Inject

class AccountViewModel @Inject constructor(var repository: YobaRepository): BaseViewModel() {

    var accountName: ObservableField<String> = ObservableField()
    var accountType: ObservableInt = ObservableInt()

    private var accountId: MutableLiveData<Long> = MutableLiveData()
    private var accountLiveData: LiveData<AccountEntity>

    init {
        accountLiveData = Transformations.switchMap(accountId, {
            if (it == null || it == 0L) {
                return@switchMap MutableLiveData<AccountEntity>().apply { value = AccountEntity() }
            } else {
                return@switchMap repository.loadAccount(it)
            }
        })
    }

    private lateinit var account: AccountEntity

    fun setAccountId(id: Long?) {
        accountId.value = id
    }

    fun getAccount(): LiveData<AccountEntity> {
        return accountLiveData
    }

    fun setAccount(account: AccountEntity) {
        this.account = account
        accountName.set(account.name)
        accountType.set(account.type)
    }

    fun saveAccount() {
        repository.createOrUpdateAccount(
                AccountEntity(id = account.id, name = accountName.get().toString(), type = accountType.get())
        )
    }
}