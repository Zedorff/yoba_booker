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
    fun loadAccountWithTransactions(id: Long): LiveData<FullAccount>

    @Query("SELECT * from accounts WHERE account_id=:id")
    fun loadAccount(id: Long): LiveData<AccountEntity>

    @Query("SELECT * from accounts")
    fun loadAllAccounts(): LiveData<List<AccountEntity>>

    @Transaction
    @Query("SELECT * from accounts")
    fun loadFullAccounts(): LiveData<List<FullAccount>>
}