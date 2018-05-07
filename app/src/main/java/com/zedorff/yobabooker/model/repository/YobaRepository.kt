package com.zedorff.yobabooker.model.repository

import android.arch.lifecycle.LiveData
import com.zedorff.yobabooker.app.enums.CategoryType
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.model.db.embeded.FullAccount
import com.zedorff.yobabooker.model.db.embeded.FullTransaction
import com.zedorff.yobabooker.model.db.embeded.FullTransfer
import com.zedorff.yobabooker.model.db.entities.AccountEntity
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import com.zedorff.yobabooker.model.db.entities.TransactionEntity
import com.zedorff.yobabooker.model.db.entities.TransferEntity

interface YobaRepository {
    fun loadTransactions(): LiveData<List<TransactionEntity>>
    fun loadFullTransactions(): LiveData<List<FullTransaction>>
    fun loadTransactionsByCategoryId(id: Long): LiveData<List<TransactionEntity>>
    fun loadTransactionsByAccountId(id: Long): LiveData<List<TransactionEntity>>
    fun loadFullTransactionsByCategoryId(id: Long): LiveData<List<FullTransaction>>
    fun loadFullTransactionsByAccountId(id: Long): LiveData<List<FullTransaction>>
    fun loadFullTransactionsByMonthInYear(month: Int, year: Int): LiveData<List<FullTransaction>>
    fun loadTransaction(id: Long): LiveData<TransactionEntity>
    fun loadFullTransfer(id: Long): LiveData<FullTransfer>
    fun deleteTransaction(transaction: TransactionEntity)

    fun loadAllAccounts(): LiveData<List<AccountEntity>>
    fun loadFullAccounts(): LiveData<List<FullAccount>>
    fun loadAccount(id: Long): LiveData<AccountEntity>
    fun loadFullAccount(id: Long): LiveData<FullAccount>
    fun updateAccounts(items: List<AccountEntity>)

    fun loadAllCategories(): LiveData<List<CategoryEntity>>
    fun loadCategoriesByType(type: TransactionType): LiveData<List<CategoryEntity>>
    fun loadCategory(id: Long): LiveData<CategoryEntity>
    fun loadCategoryByInternalType(type: CategoryType): LiveData<CategoryEntity>
    fun updateCategories(items: List<CategoryEntity>)

    fun loadTransfer(id: Long): LiveData<TransferEntity>

    fun createOrUpdateAccount(account: AccountEntity)
    fun createOrUpdateTransaction(transaction: TransactionEntity)
    fun createOrUpdateTransfer(transfer: TransferEntity, from: TransactionEntity, to: TransactionEntity)
}