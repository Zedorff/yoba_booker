package com.zedorff.yobabooker.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.zedorff.yobabooker.model.db.embeded.FullAccount
import com.zedorff.yobabooker.model.db.entities.AccountEntity

@Dao
interface AccountDao: BaseDao<AccountEntity> {

    @Transaction
    @Query("SELECT * from accounts WHERE account_id=:id")
    fun loadAccountWithTransactions(id: Long): LiveData<FullAccount>

    @Query("SELECT * from accounts WHERE account_id=:id")
    fun loadAccount(id: Long): LiveData<AccountEntity>

    @Query("SELECT * from accounts ORDER BY account_order")
    fun loadAllAccounts(): LiveData<List<AccountEntity>>

    @Transaction
    @Query("SELECT * from accounts ORDER BY account_order")
    fun loadFullAccounts(): LiveData<List<FullAccount>>

    @Query("SELECT MAX(account_order) FROM accounts")
    fun getAccountMaxOrder(): Int

    @Transaction
    @Update
    fun update(items: Collection<AccountEntity>)
}