package com.zedorff.yobabooker.model.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zedorff.yobabooker.app.enums.TransactionType

class Migrations {
    companion object {
        val MIGRATION_FROM_1_TO_2 = object: Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE transactions ADD COLUMN transaction_type INTEGER NOT NULL DEFAULT " + TransactionType.EMPTY.value)
            }
        }

        val MIGRATION_FROM_2_TO_3 = object: Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE accounts ADD COLUMN account_order INTEGER NOT NULL DEFAULT 0")
            }
        }
     }
}