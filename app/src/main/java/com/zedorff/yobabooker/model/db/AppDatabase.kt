package com.zedorff.yobabooker.model.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.zedorff.yobabooker.model.db.dao.SpendingDao
import com.zedorff.yobabooker.model.db.entities.SpendingEntity

@Database(entities = [(SpendingEntity::class)], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun provideSpendingDao(): SpendingDao
}