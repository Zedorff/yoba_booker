package com.zedorff.yobabooker.model.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import com.zedorff.yobabooker.model.db.embeded.FullAccount
import com.zedorff.yobabooker.model.db.entities.AccountEntity

@Dao
interface AccountDao: BaseDao<AccountEntity> {

    @Transaction
    @Query("SELECT * from accounts WHERE account_id=:id")
    fun getAccountWithTransactions(id: String): LiveData<FullAccount>

    @Query("SELECT * from accounts WHERE account_id=:id")
    fun getAccount(id: String): LiveData<AccountEntity>

    @Query("SELECT * from accounts")
    fun getAllAccounts(): LiveData<List<AccountEntity>>

    @Transaction
    @Query("SELECT * from accounts")
    fun getFullAccounts(): LiveData<List<FullAccount>>
}