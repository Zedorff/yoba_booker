package com.zedorff.yobabooker.model.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transfers")
data class TransferEntity(@PrimaryKey(autoGenerate = true)
                          @ColumnInfo(name = "transfer_id")
                          var id: Long = 0,
                          @ColumnInfo(name = "from_account_id")
                          var fromAccountId: Long = 0,
                          @ColumnInfo(name = "to_account_id")
                          var toAccountId: Long = 0)