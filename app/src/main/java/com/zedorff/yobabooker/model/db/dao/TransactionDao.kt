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
    fun getTransaction(id: String): LiveData<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE category_id_relation=:id")
    fun getTransactionsByCategoryId(id: String): LiveData<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE account_id_relation=:id")
    fun getTransactionsByAccountId(id: String): LiveData<List<TransactionEntity>>

    @Query("SELECT * FROM transactions")
    fun getTransactions(): LiveData<List<TransactionEntity>>

    @Query("SELECT * FROM transactions " +
            "JOIN categories ON category_id_relation = category_id " +
            "JOIN accounts ON account_id_relation = account_id")
    @Transaction
    fun getFullTransactions(): LiveData<List<FullTransaction>>

    @Query("SELECT * FROM transactions " +
            "JOIN categories ON category_id_relation = category_id " +
            "JOIN accounts ON account_id_relation = account_id " +
            "WHERE category_id_relation=:id")
    @Transaction
    fun getFullTransactionsByCategoryId(id: String): LiveData<List<FullTransaction>>

    @Query("SELECT * FROM transactions " +
            "JOIN categories ON category_id_relation = category_id " +
            "JOIN accounts ON account_id_relation = account_id " +
            "WHERE account_id_relation=:id")
    @Transaction
    fun getFullTransactionsByAccountId(id: String): LiveData<List<FullTransaction>>
}