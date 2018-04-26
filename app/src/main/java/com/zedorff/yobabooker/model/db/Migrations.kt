package com.zedorff.yobabooker.model.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration
import android.graphics.Color
import com.zedorff.yobabooker.app.enums.TransactionType

class Migrations {
    companion object {
        val MIGRATION_FROM_1_TO_2 = object: Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE transactions ADD COLUMN transaction_type INTEGER NOT NULL DEFAULT " + TransactionType.EMPTY.value)
            }
        }
     }
}