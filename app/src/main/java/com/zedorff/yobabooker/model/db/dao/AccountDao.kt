package com.zedorff.yobabooker.model.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import android.arch.persistence.room.Update
import com.zedorff.yobabooker.model.db.embeded.FullAccount
import com.zedorff.yobabooker.model.db.entities.AccountEntity

@Dao
interface AccountDao: BaseDao<AccountEntity> {

    @Transaction
    @Query("SELECT * from accounts WHERE account_id=:id")
    fun getFullAccount(id: String): LiveData<FullAccount>

    @Query("SELECT * from accounts WHERE account_id=:id")
    fun getAccount(id: String): LiveData<AccountEntity>

    @Query("SELECT * from accounts ORDER BY account_order")
    fun getAllAccounts(): LiveData<List<AccountEntity>>

    @Transaction
    @Query("SELECT * from accounts ORDER BY account_order")
    fun getFullAccounts(): LiveData<List<FullAccount>>

    @Query("SELECT MAX(account_order) FROM accounts")
    fun getAccountMaxOrder(): Int

    @Transaction
    @Update
    fun update(items: Collection<AccountEntity>)
}