package com.zedorff.yobabooker.model.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.zedorff.yobabooker.model.db.dao.AccountDao
import com.zedorff.yobabooker.model.db.dao.CategoryDao
import com.zedorff.yobabooker.model.db.dao.TransactionDao
import com.zedorff.yobabooker.model.db.dao.TransferDao
import com.zedorff.yobabooker.model.db.entities.AccountEntity
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import com.zedorff.yobabooker.model.db.entities.TransactionEntity
import com.zedorff.yobabooker.model.db.entities.TransferEntity

@Database(entities = [(TransactionEntity::class), (AccountEntity::class), (CategoryEntity::class), (TransferEntity::class)], version = 3)
abstract class AppDatabase: RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transferDao(): TransferDao
}