package com.zedorff.yobabooker.model.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "accounts")
class AccountEntity(@PrimaryKey(autoGenerate = true) var id: Int, var name: String, var type: Int = 0)