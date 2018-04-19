package com.zedorff.yobabooker.ui.activities.newtransaction.fragments.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.zedorff.yobabooker.app.extensions.toNegative
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import com.zedorff.yobabooker.model.db.entities.TransactionEntity
import com.zedorff.yobabooker.model.repository.YobaRepository
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import javax.inject.Inject

class NewTransactionViewModel @Inject constructor(var repository: YobaRepository) : BaseViewModel() {

    var accounts = repository.getAllAccounts()
    var accountsData: LiveData<List<String>> = Transformations.map(accounts, { it.map { it.name } })
    var transactionCost: MutableLiveData<String> = MutableLiveData()
    var transactionDescription: MutableLiveData<String> = MutableLiveData()
    var transactionCategory: MutableLiveData<Int> = MutableLiveData()
    var transactionAccount: MutableLiveData<Int> = MutableLiveData()
    lateinit var categories: LiveData<List<CategoryEntity>>
    lateinit var categoriesData: LiveData<List<String>>
    private var income: Boolean = false


    fun setIsIncome(isIncome: Boolean) {
        this.income = isIncome
        categories = if (isIncome) {
            repository.getIncomeCategories()
        } else {
            repository.getOutcomeCategories()
        }
        categoriesData = Transformations.map(categories, { it.map { it.name } })
    }

    fun saveTransaction() {
        val selectedCategory = transactionCategory.value
                ?: throw IllegalStateException("Category can't be null!")
        val selectedAccount = transactionAccount.value
                ?: throw IllegalStateException("Account can't be null!")
        async(CommonPool) {
            repository.createTransaction(TransactionEntity(
                    description = transactionDescription.value,
                    value = (transactionCost.value?.toFloat() ?: 0f) * if (income) 1 else -1,
                    categoryId = categories.value?.get(selectedCategory)?.id
                            ?: throw IllegalStateException("Categories can't be null!!"),
                    date = System.currentTimeMillis() / 1000L,
                    accountId = accounts.value?.get(selectedAccount)?.id
                            ?: throw IllegalStateException("Accounts can't be null!")))
        }
    }
}