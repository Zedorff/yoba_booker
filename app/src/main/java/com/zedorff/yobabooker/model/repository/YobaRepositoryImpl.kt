package com.zedorff.yobabooker.model.repository

import com.zedorff.yobabooker.app.enums.CategoryType
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.model.db.dao.AccountDao
import com.zedorff.yobabooker.model.db.dao.CategoryDao
import com.zedorff.yobabooker.model.db.dao.TransactionDao
import com.zedorff.yobabooker.model.db.dao.TransferDao
import com.zedorff.yobabooker.model.db.entities.AccountEntity
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import com.zedorff.yobabooker.model.db.entities.TransactionEntity
import com.zedorff.yobabooker.model.db.entities.TransferEntity
import kotlinx.coroutines.experimental.async
import javax.inject.Inject

class YobaRepositoryImpl @Inject constructor(
        private var accountDao: AccountDao,
        private var categoryDao: CategoryDao,
        private var transactionDao: TransactionDao,
        private var transferDao: TransferDao) : YobaRepository {

    override fun loadTransactions() = transactionDao.loadTransactions()
    override fun loadFullTransactions() = transactionDao.loadFullTransactions()
    override fun loadTransactionsByCategoryId(id: Long) = transactionDao.loadTransactionsByCategoryId(id)
    override fun loadTransactionsByAccountId(id: Long) = transactionDao.loadTransactionsByAccountId(id)
    override fun loadFullTransactionsByCategoryId(id: Long) = transactionDao.loadFullTransactionsByCategoryId(id)
    override fun loadFullTransactionsByAccountId(id: Long) = transactionDao.loadFullTransactionsByAccountId(id)
    override fun loadFullTransactionsByMonthInYear(month: Int, year: Int) = transactionDao.loadFullTransactionsPerMonth(month, year)
    override fun loadTransaction(id: Long) = transactionDao.loadTransaction(id)
    override fun loadFullTransfer(id: Long) = transferDao.loadFullTransfer(id)

    override fun loadAllAccounts() = accountDao.loadAllAccounts()
    override fun loadFullAccounts() = accountDao.loadFullAccounts()
    override fun loadAccount(id: Long) = accountDao.loadAccount(id)
    override fun loadFullAccount(id: Long) = accountDao.loadAccountWithTransactions(id)
    override fun updateAccounts(items: List<AccountEntity>) = accountDao.update(items)

    override fun loadAllCategories() = categoryDao.loadAllCategories()
    override fun loadCategoriesByType(type: TransactionType) = categoryDao.loadCategoriesByType(type)
    override fun loadCategory(id: Long) = categoryDao.loadCategory(id)
    override fun loadCategoryByInternalType(type: CategoryType) = categoryDao.loadCategoryByInternalType(type)
    override fun updateCategories(items: List<CategoryEntity>) = categoryDao.update(items)

    override fun loadTransfer(id: Long) = transferDao.loadTransfer(id)

    override fun deleteTransaction(transaction: TransactionEntity) {
        transaction.transferId?.let {
            transferDao.delete(it)
        } ?: transactionDao.delete(transaction)
    }

    override fun createOrUpdateAccount(account: AccountEntity) {
        async {
            if (account.id > 0) {
                accountDao.update(account)
            } else {
                with(account) {
                    order = accountDao.getAccountMaxOrder()
                    accountDao.insert(this)
                }
            }
        }
    }

    override fun createOrUpdateTransaction(transaction: TransactionEntity) {
        async {
            if (transaction.id > 0) {
                transactionDao.update(transaction)
            } else {
                transactionDao.insert(transaction)
            }
        }
    }

    override fun createOrUpdateTransfer(transfer: TransferEntity, from: TransactionEntity, to: TransactionEntity) {
        async {
            if (transfer.id > 0) {
                transferDao.update(transfer)
                from.transferId = transfer.id
                to.transferId = transfer.id
            } else {
                val id = transferDao.insert(transfer)
                from.transferId = id
                to.transferId = id
            }
            createOrUpdateTransaction(to)
            createOrUpdateTransaction(from)
        }
    }
}