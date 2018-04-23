package com.zedorff.yobabooker.model.repository

import android.arch.lifecycle.LiveData
import com.zedorff.yobabooker.app.extensions.asyncAwait
import com.zedorff.yobabooker.model.db.dao.AccountDao
import com.zedorff.yobabooker.model.db.dao.CategoryDao
import com.zedorff.yobabooker.model.db.dao.TransactionDao
import com.zedorff.yobabooker.model.db.embeded.FullAccount
import com.zedorff.yobabooker.model.db.embeded.FullTransaction
import com.zedorff.yobabooker.model.db.entities.AccountEntity
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import com.zedorff.yobabooker.model.db.entities.TransactionEntity
import com.zedorff.yobabooker.ui.activities.transaction.TransactionActivity
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import javax.inject.Inject

class YobaRepositoryImpl @Inject constructor(
        private var accountDao: AccountDao,
        private var categoryDao: CategoryDao,
        private var transactionDao: TransactionDao) : YobaRepository {

    override fun getTransactions(): LiveData<List<TransactionEntity>> = transactionDao.getTransactions()
    override fun getFullTransactions(): LiveData<List<FullTransaction>> = transactionDao.getFullTransactions()
    override fun getTransactionsByCategoryId(id: String): LiveData<List<TransactionEntity>> = transactionDao.getTransactionsByCategoryId(id)
    override fun getTransactionsByAccountId(id: String): LiveData<List<TransactionEntity>> = transactionDao.getTransactionsByAccountId(id)
    override fun getFullTransactionsByCategoryId(id: String): LiveData<List<FullTransaction>> = transactionDao.getFullTransactionsByCategoryId(id)
    override fun getFullTransactionsByAccountId(id: String): LiveData<List<FullTransaction>> = transactionDao.getFullTransactionsByAccountId(id)
    override fun getFullTransactionsByMonthInYear(month: Int, year: Int): LiveData<List<FullTransaction>> = transactionDao.getFullTransactionsPerMonth(month, year)

    override fun getTransaction(id: String): LiveData<TransactionEntity> = transactionDao.getTransaction(id)

    override fun getAllAccounts(): LiveData<List<AccountEntity>> = accountDao.getAllAccounts()
    override fun getFullAccounts(): LiveData<List<FullAccount>> = accountDao.getFullAccounts()
    override fun getAccount(id: String): LiveData<AccountEntity> = accountDao.getAccount(id)
    override fun getFullAccount(id: String): LiveData<FullAccount> = accountDao.getAccountWithTransactions(id)

    override fun getAllCategories(): LiveData<List<CategoryEntity>> = categoryDao.getAllCategories()
    override fun getCategoriesByType(type: TransactionActivity.TransactionType): LiveData<List<CategoryEntity>> = categoryDao.getCategoriesByType(type)

    override fun getCategory(id: String): LiveData<CategoryEntity> = categoryDao.getCategory(id)

    override suspend fun createOrUpdateAccount(account: AccountEntity) {
        asyncAwait {
            if (account.id > 0) {
                accountDao.update(account)
            } else {
                accountDao.insert(account)
            }
        }
    }

    override suspend fun createOrUpdateTransaction(transaction: TransactionEntity) {
        asyncAwait {
            if (transaction.id > 0) {
                transactionDao.update(transaction)
            } else {
                transactionDao.insert(transaction)
            }
        }
    }
}