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
                     var name: String)

