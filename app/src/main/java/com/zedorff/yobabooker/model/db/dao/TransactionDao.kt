package com.zedorff.yobabooker.model.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import com.zedorff.yobabooker.model.db.embeded.FullTransaction
import com.zedorff.yobabooker.model.db.entities.TransactionEntity

@Dao
interface TransactionDao: BaseDao<TransactionEntity> {

    @Query("SELECT * FROM transactions WHERE transaction_id=:id")
    fun loadTransaction(id: Long): LiveData<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE category_id_relation=:id")
    fun loadTransactionsByCategoryId(id: Long): LiveData<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE account_id_relation=:id")
    fun loadTransactionsByAccountId(id: Long): LiveData<List<TransactionEntity>>

    @Query("SELECT * FROM transactions")
    fun loadTransactions(): LiveData<List<TransactionEntity>>

    @Transaction
    @Query("SELECT * FROM transactions JOIN categories ON category_id_relation = category_id JOIN accounts ON account_id_relation = account_id ORDER BY transaction_date")
    fun loadFullTransactions(): LiveData<List<FullTransaction>>

    @Transaction
    @Query("SELECT * FROM transactions JOIN categories ON category_id_relation = category_id JOIN accounts ON account_id_relation = account_id WHERE category_id_relation=:id")
    fun loadFullTransactionsByCategoryId(id: Long): LiveData<List<FullTransaction>>

    @Transaction
    @Query("SELECT * FROM transactions JOIN categories ON category_id_relation = category_id JOIN accounts ON account_id_relation = account_id WHERE account_id_relation=:id")
    fun loadFullTransactionsByAccountId(id: Long): LiveData<List<FullTransaction>>

    @Transaction
    @Query("SELECT * FROM transactions JOIN categories ON category_id_relation = category_id JOIN accounts ON account_id_relation = account_id WHERE cast(strftime('%m', datetime(transaction_date / 1000, 'unixepoch', 'localtime')) as INTEGER) =:month AND cast(strftime('%Y', datetime(transaction_date / 1000, 'unixepoch', 'localtime')) as INTEGER) =:year")
    fun loadFullTransactionsPerMonth(month: Int, year: Int): LiveData<List<FullTransaction>>
}