package com.zedorff.yobabooker.model.db.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "categories")
class CategoryEntity(@PrimaryKey(autoGenerate = true)
                     @ColumnInfo(name = "category_id")
                     var id: Int = 0,
                     @ColumnInfo(name = "category_type")
                     var type: Int,
                     @ColumnInfo(name = "category_name")
                     var name: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CategoryEntity

        if (id != other.id) return false
        if (type != other.type) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + type
        result = 31 * result + name.hashCode()
        return result
    }
}

