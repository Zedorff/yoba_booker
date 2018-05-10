package com.zedorff.yobabooker.ui.activities.transaction.fragments.viewmodel

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.databinding.ObservableLong
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.model.db.entities.AccountEntity
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import com.zedorff.yobabooker.model.db.entities.TransactionEntity
import com.zedorff.yobabooker.model.repository.YobaRepository
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import javax.inject.Inject

//TODO refactor and rethink this pizdec
//Looks good as for now
class TransactionViewModel @Inject constructor(var repository: YobaRepository) : BaseViewModel() {

    private var observableAccounts: LiveData<List<AccountEntity>> = MutableLiveData()
    private var observableCategories: LiveData<List<CategoryEntity>> = MutableLiveData()
    private var observableTransaction: LiveData<TransactionEntity> = MutableLiveData()
    private var observableTransactionId: MutableLiveData<Long> = MutableLiveData()
    private var observableTransactionType: MutableLiveData<TransactionType> = MutableLiveData()

    var accounts: ObservableField<List<String>> = ObservableField()
    var categories: ObservableField<List<String>> = ObservableField()

    var transactionCost: ObservableField<String> = ObservableField()
    var transactionDescription: ObservableField<String> = ObservableField()
    var transactionCategory: ObservableInt = ObservableInt()
    var transactionAccount: ObservableInt = ObservableInt()
    var transactionDate: ObservableLong = ObservableLong()

    private lateinit var transaction: TransactionEntity

    fun getTransaction() = observableTransaction
    fun getAccounts() = observableAccounts
    fun getCategories() = observableCategories
    fun getDate(): Long = transactionDate.get()

    init {
        observableAccounts = repository.loadAllAccounts()
        observableCategories = Transformations.switchMap(observableTransactionType, {
            repository.loadCategoriesByType(it)
        })

        observableTransaction = Transformations.switchMap(observableTransactionId, {
            if (it == null || it == 0L) {
                return@switchMap MutableLiveData<TransactionEntity>().apply { value = TransactionEntity() }
            } else {
                return@switchMap repository.loadTransaction(it)
            }
        })
    }

    fun setTransactionId(id: Long?) {
        observableTransactionId.value = id
    }

    fun setTransactionType(type: TransactionType) {
        observableTransactionType.value = type
    }

    fun setTransaction(transaction: TransactionEntity) {
        this.transaction = transaction
        with(transaction) {
            if (value != 0f) {
                if (value % 1 != 0F) {
                    transactionCost.set(Math.abs(value).toString())
                } else {
                    transactionCost.set(Math.abs(Math.round(value)).toString())
                }
            }
            transactionDescription.set(description)
            transactionDate.set(date)
        }
    }

    fun setAccounts(items: List<AccountEntity>) {
        accounts.set(items.map { it.name })
        items.forEachIndexed { index, accountEntity ->
            if (accountEntity.id == transaction.accountId)
                transactionAccount.set(index)
        }
    }

    fun setCategories(items: List<CategoryEntity>) {
        categories.set(items.map { it.name })
        items.forEachIndexed { index, categoryEntity ->
            if (categoryEntity.id == transaction.categoryId)
                transactionCategory.set(index)
        }
    }

    fun setDate(millis: Long) {
        transactionDate.set(millis)
    }

    fun saveTransaction() {
        val selectedCategory = transactionCategory.get()
        val selectedAccount = transactionAccount.get()
        repository.createOrUpdateTransaction(TransactionEntity(
                id = transaction.id,
                description = transactionDescription.get(),
                value = transactionCost.get()!!.toFloat() * if (observableTransactionType.value == TransactionType.INCOME) 1 else -1,
                categoryId = observableCategories.value!![selectedCategory].id,
                date = transactionDate.get(),
                type = observableTransactionType.value!!,
                accountId = observableAccounts.value!![selectedAccount].id))
    }
}