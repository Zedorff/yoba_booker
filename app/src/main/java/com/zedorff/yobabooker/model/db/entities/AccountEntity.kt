package com.zedorff.yobabooker.model.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity(@PrimaryKey(autoGenerate = true)
                         @ColumnInfo(name = "account_id")
                         var id: Long = 0,
                         @ColumnInfo(name = "account_name")
                         var name: String = "",
                         @ColumnInfo(name = "account_type")
                         var type: Int = 0,
                         @ColumnInfo(name = "account_order")
                         var order: Int = 0)

