package com.zedorff.yobabooker.model.repository

import android.arch.lifecycle.LiveData
import com.zedorff.yobabooker.model.db.embeded.FullAccount
import com.zedorff.yobabooker.model.db.embeded.FullTransaction
import com.zedorff.yobabooker.model.db.entities.AccountEntity
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import com.zedorff.yobabooker.model.db.entities.TransactionEntity
import kotlinx.coroutines.experimental.Deferred

interface YobaRepository {
    fun getTransactions(): LiveData<List<TransactionEntity>>
    fun getFullTransactions(): LiveData<List<FullTransaction>>
    fun getTransactionsByCategoryId(id: String): LiveData<List<TransactionEntity>>
    fun getTransactionsByAccountId(id: String): LiveData<List<TransactionEntity>>
    fun getFullTransactionsByCategoryId(id: String): LiveData<List<FullTransaction>>
    fun getFullTransactionsByAccountId(id: String): LiveData<List<FullTransaction>>
    fun getTransaction(id: String): LiveData<TransactionEntity>

    fun getAllAccounts(): LiveData<List<AccountEntity>>
    fun getFullAccounts(): LiveData<List<FullAccount>>
    fun getAccount(id: String): LiveData<AccountEntity>
    fun getFullAccount(id: String): LiveData<FullAccount>

    fun getAllCategories(): LiveData<List<CategoryEntity>>
    fun getIncomeCategories(): LiveData<List<CategoryEntity>>
    fun getOutcomeCategories(): LiveData<List<CategoryEntity>>
    fun getCategory(id: String): LiveData<CategoryEntity>

    fun createAccount(account: AccountEntity): Deferred<Long>
    fun createTransaction(transaction: TransactionEntity): Deferred<Long>
}