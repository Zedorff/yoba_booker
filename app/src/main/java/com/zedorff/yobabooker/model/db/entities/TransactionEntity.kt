package com.zedorff.yobabooker.model.db.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "transactions")
class TransactionEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int
    var name: String
    var value: Float
    var date: Long

    @ColumnInfo(name = "account_id")
    var accountId: Int
    @ColumnInfo(name = "category_id")
    var categoryId: Int

    constructor(id: Int, name: String, value: Float, date: Long, accountId: Int, categoryId: Int) {
        this.id = id
        this.name = name
        this.value = value
        this.date = date
        this.accountId = accountId
        this.categoryId = categoryId
    }
}