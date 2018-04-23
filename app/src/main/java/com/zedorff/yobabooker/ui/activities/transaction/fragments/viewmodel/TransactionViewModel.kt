package com.zedorff.yobabooker.ui.activities.transaction.fragments.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.Transformations
import com.zedorff.yobabooker.model.db.entities.AccountEntity
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import com.zedorff.yobabooker.model.db.entities.TransactionEntity
import com.zedorff.yobabooker.model.repository.YobaRepository
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import com.zedorff.yobabooker.ui.activities.transaction.TransactionActivity.TransactionType
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import javax.inject.Inject

//TODO refactor and rethink this pizdec
class TransactionViewModel @Inject constructor(var repository: YobaRepository) : BaseViewModel() {

    var accounts: LiveData<List<AccountEntity>> = repository.getAllAccounts()
    var accountsData: LiveData<List<String>>
    var categories: LiveData<List<CategoryEntity>>
    var categoriesData: LiveData<List<String>>

    var transactionCost: MutableLiveData<String> = MutableLiveData()
    var transactionDescription: MutableLiveData<String> = MutableLiveData()
    var transactionCategory: MutableLiveData<Int> = MutableLiveData()
    var transactionAccount: MutableLiveData<Int> = MutableLiveData()

    private var transactionDate: MutableLiveData<Long> = MutableLiveData()
    private var transactionId: MutableLiveData<String?> = MutableLiveData()
    private var transactionLiveData: LiveData<TransactionEntity>
    private var transactionType: MutableLiveData<TransactionType> = MutableLiveData()

    private lateinit var categoriesObserver: Observer<List<CategoryEntity>>
    private lateinit var accountObserver: Observer<List<AccountEntity>>

    private lateinit var transaction: TransactionEntity

    init {
        accountsData = Transformations.map(accounts, { it.map { it.name } })
        transactionLiveData = Transformations.switchMap(transactionId, {
            if (it == null) {
                return@switchMap MutableLiveData<TransactionEntity>().apply { value = TransactionEntity() }
            } else {
                return@switchMap repository.getTransaction(it)
            }
        })
        categories = Transformations.switchMap(transactionType, {
            repository.getCategoriesByType(it)
        })

        categoriesData = Transformations.map(categories, { it.map { it.name } })
    }

    fun setTransaction(transaction: TransactionEntity, type: TransactionType) {
        this.transaction = transaction
        transactionType.value = type
        transactionCost.value = Math.abs(transaction.value).toString()
        transactionDescription.value = transaction.description
        transactionDate.value = if (transaction.date == 0L) System.currentTimeMillis() else transaction.date

        categoriesObserver = Observer {
            it?.forEachIndexed { index, categoryEntity ->
                if (categoryEntity.id == transaction.categoryId)
                    transactionCategory.value = index
            }
            categories.removeObserver(categoriesObserver)

        }
        categories.observeForever(categoriesObserver)

        accountObserver = Observer {
            it?.forEachIndexed { index, accountEntity ->
                if (accountEntity.id == transaction.accountId)
                    transactionAccount.value = index
            }
            accounts.removeObserver(accountObserver)

        }
        accounts.observeForever(accountObserver)
    }

    fun setTransactionId(id: String?) {
        transactionId.value = id
    }

    fun getTransaction(): LiveData<TransactionEntity> {
        return transactionLiveData
    }

    fun getDate(): LiveData<Long> = transactionDate

    fun setDate(millis: Long) {
        transactionDate.value = millis
    }

    //TODO get rid of this elvis guys
    fun saveTransaction() {
        val selectedCategory = transactionCategory.value
                ?: throw IllegalStateException("Category can't be null!")
        val selectedAccount = transactionAccount.value
                ?: throw IllegalStateException("Account can't be null!")
        async(CommonPool) {
            repository.createOrUpdateTransaction(TransactionEntity(
                    id = transaction.id,
                    description = transactionDescription.value,
                    value = (transactionCost.value?.toFloat()
                            ?: 0f) * if (transactionType.value == TransactionType.INCOME) 1 else -1,
                    categoryId = categories.value?.get(selectedCategory)?.id
                            ?: throw IllegalStateException("Categories can't be null!!"),
                    date = transactionDate.value
                            ?: throw IllegalStateException("Date can't be null!!"),
                    accountId = accounts.value?.get(selectedAccount)?.id
                            ?: throw IllegalStateException("Accounts can't be null!")))
        }
    }
}