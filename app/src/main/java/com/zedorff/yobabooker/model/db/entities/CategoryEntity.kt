package com.zedorff.yobabooker.model.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "categories")
class CategoryEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var type: Int
    var name: String

    constructor(type: Int, name: String) {
        this.type = type
        this.name = name
    }
}