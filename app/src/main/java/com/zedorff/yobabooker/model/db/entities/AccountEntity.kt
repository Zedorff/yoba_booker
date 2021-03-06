package com.zedorff.yobabooker.model.db.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "accounts")
class AccountEntity(@PrimaryKey(autoGenerate = true)
                    @ColumnInfo(name = "account_id")
                    var id: Int = 0,
                    @ColumnInfo(name = "account_name")
                    var name: String,
                    @ColumnInfo(name = "account_type")
                    var type: Int = 0)