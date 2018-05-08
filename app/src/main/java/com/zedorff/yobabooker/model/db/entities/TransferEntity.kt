package com.zedorff.yobabooker.model.db.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "transfers")
data class TransferEntity(@PrimaryKey(autoGenerate = true)
                          @ColumnInfo(name = "transfer_id")
                          var id: Long = 0,
                          @ColumnInfo(name = "from_account_id")
                          var fromAccountId: Long = 0,
                          @ColumnInfo(name = "to_account_id")
                          var toAccountId: Long = 0)