package com.zedorff.yobabooker.model.repository

import android.arch.lifecycle.LiveData
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.model.db.embeded.FullAccount
import com.zedorff.yobabooker.model.db.embeded.FullTransaction
import com.zedorff.yobabooker.model.db.entities.AccountEntity
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import com.zedorff.yobabooker.model.db.entities.TransactionEntity

interface YobaRepository {
    fun getTransactions(): LiveData<List<TransactionEntity>>
    fun getFullTransactions(): LiveData<List<FullTransaction>>
    fun getTransactionsByCategoryId(id: String): LiveData<List<TransactionEntity>>
    fun getTransactionsByAccountId(id: String): LiveData<List<TransactionEntity>>
    fun getFullTransactionsByCategoryId(id: String): LiveData<List<FullTransaction>>
    fun getFullTransactionsByAccountId(id: String): LiveData<List<FullTransaction>>
    fun getFullTransactionsByMonthInYear(month: Int, year: Int): LiveData<List<FullTransaction>>
    fun getTransaction(id: String): LiveData<TransactionEntity>
    fun deleteTransaction(transaction: TransactionEntity)

    fun getAllAccounts(): LiveData<List<AccountEntity>>
    fun getFullAccounts(): LiveData<List<FullAccount>>
    fun getAccount(id: String): LiveData<AccountEntity>
    fun getFullAccount(id: String): LiveData<FullAccount>
    fun updateAccounts(items: List<AccountEntity>)

    fun getAllCategories(): LiveData<List<CategoryEntity>>
    fun getCategoriesByType(type: TransactionType): LiveData<List<CategoryEntity>>
    fun getCategory(id: String): LiveData<CategoryEntity>
    fun updateCategories(items: List<CategoryEntity>)

    suspend fun createOrUpdateAccount(account: AccountEntity)
    suspend fun createOrUpdateTransaction(transaction: TransactionEntity)
}