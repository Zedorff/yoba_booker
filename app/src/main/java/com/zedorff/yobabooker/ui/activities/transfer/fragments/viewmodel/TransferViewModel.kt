package com.zedorff.yobabooker.ui.activities.transfer.fragments.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.databinding.ObservableLong
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.di.qualifiers.AppContext
import com.zedorff.yobabooker.app.enums.CategoryType
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.app.extensions.negate
import com.zedorff.yobabooker.model.db.embeded.FullTransfer
import com.zedorff.yobabooker.model.db.entities.AccountEntity
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import com.zedorff.yobabooker.model.repository.YobaRepository
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
class TransferViewModel @Inject constructor(var repository: YobaRepository, @AppContext var context: Context) : BaseViewModel() {

    private var observableAccounts: LiveData<List<AccountEntity>> = MutableLiveData()
    private var observableOutCategory: LiveData<CategoryEntity> = MutableLiveData()
    private var observableInCategory: LiveData<CategoryEntity> = MutableLiveData()
    private var observableTransfer: LiveData<FullTransfer> = MutableLiveData()
    private var observableTransferId: MutableLiveData<Long> = MutableLiveData()

    var accounts: ObservableField<List<String>> = ObservableField()

    var transferDate = ObservableLong()
    var transferCost: ObservableField<String> = ObservableField()
    var fromAccount: ObservableInt = ObservableInt()
    var toAccount: ObservableInt = ObservableInt()

    private var inCategoryId: Long = 0L
    private var outCategoryId: Long = 0L

    private lateinit var transferEntity: FullTransfer

    fun getDate(): Long = transferDate.get()
    fun getAccounts() = observableAccounts
    fun getInCategory() = observableInCategory
    fun getOutCategory() = observableOutCategory
    fun getTransfer() = observableTransfer

    init {
        observableAccounts = repository.loadAllAccounts()
        observableTransfer = Transformations.switchMap(observableTransferId, {
            if (it == null || it == 0L) {
                return@switchMap MutableLiveData<FullTransfer>().apply { value = FullTransfer() }
            } else {
                return@switchMap repository.loadFullTransfer(it)
            }
        })
        observableOutCategory = repository.loadCategoryByInternalType(CategoryType.TRANSFER_OUT)
        observableInCategory = repository.loadCategoryByInternalType(CategoryType.TRANSFER_IN)
    }

    fun setTransferId(id: Long?) {
        observableTransferId.value = id
    }

    fun setTransfer(transfer: FullTransfer) {
        transferEntity = transfer
        with(transfer.transactions.first()) {
            if (value != 0f) {
                if (value % 1 != 0F) {
                    transferCost.set(Math.abs(value).toString())
                } else {
                    transferCost.set(Math.abs(Math.round(value)).toString())
                }
            }
            transferDate.set(date)
        }
    }

    fun setAccounts(items: List<AccountEntity>) {
        accounts.set(items.map { it.name })
        items.forEachIndexed { index, accountEntity ->
            if (accountEntity.id == transferEntity.transfer.fromAccountId)
                fromAccount.set(index)
            if (accountEntity.id == transferEntity.transfer.toAccountId)
                toAccount.set(index)
        }
    }

    fun setInCategory(id: Long) {
        inCategoryId = id
    }

    fun setOutCategory(id: Long) {
        outCategoryId = id
    }

    fun setDate(millis: Long) {
        transferDate.set(millis)
    }

    fun saveTransfer() {
        with(transferEntity) {
            transfer.fromAccountId = observableAccounts.value!![fromAccount.get()].id
            transfer.toAccountId = observableAccounts.value!![toAccount.get()].id

            transactions.forEach {
                if (it.type == TransactionType.OUTCOME) {
                    it.description = getDescriptionOut()
                    it.value = Math.abs(transferCost.get()!!.toFloat()).negate()
                    it.categoryId = outCategoryId
                    it.accountId = observableAccounts.value!![fromAccount.get()].id
                    it.date = transferDate.get()
                } else if (it.type == TransactionType.INCOME) {
                    it.description = getDescriptionIn()
                    it.value = Math.abs(transferCost.get()!!.toFloat())
                    it.categoryId = inCategoryId
                    it.accountId = observableAccounts.value!![toAccount.get()].id
                    it.date = transferDate.get()
                }
            }
        }
        repository.createOrUpdateTransfer(transferEntity.transfer,
                transferEntity.transactions.first { it.type == TransactionType.OUTCOME },
                transferEntity.transactions.first { it.type == TransactionType.INCOME })
    }

    private fun getDescriptionOut(): String {
        return context.resources.getString(R.string.text_transfer_description_out,
                observableAccounts.value!![toAccount.get()].name)
    }

    private fun getDescriptionIn(): String {
        return context.resources.getString(R.string.text_transfer_description_in,
                observableAccounts.value!![fromAccount.get()].name)
    }
}
