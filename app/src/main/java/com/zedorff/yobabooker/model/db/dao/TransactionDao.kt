package com.zedorff.yobabooker.model.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.zedorff.yobabooker.model.db.entities.TransactionEntity

@Dao
interface TransactionDao: BaseDao<TransactionEntity> {

    @Query("SELECT * FROM transactions WHERE id=:id")
    fun getTransaction(id: String): LiveData<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE category_id=:id")
    fun getTransactionsByCategoryId(id: String): LiveData<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE account_id=:id")
    fun getTransactionsByAccoutId(id: String): LiveData<List<TransactionEntity>>

    @Query("SELECT * FROM transactions")
    fun getTransactions(): LiveData<List<TransactionEntity>>
}