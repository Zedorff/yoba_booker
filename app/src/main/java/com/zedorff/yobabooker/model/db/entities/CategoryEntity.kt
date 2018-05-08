package com.zedorff.yobabooker.model.db.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.graphics.Color
import com.zedorff.yobabooker.app.enums.CategoryType
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.model.db.converters.BaseConverters
import com.zedorff.yobabooker.model.db.converters.CategoryTypeConverter
import com.zedorff.yobabooker.model.db.converters.TransactionTypeConverter

@Entity(tableName = "categories")
@TypeConverters(TransactionTypeConverter::class, BaseConverters::class, CategoryTypeConverter::class)
data class CategoryEntity(@PrimaryKey(autoGenerate = true)
                          @ColumnInfo(name = "category_id")
                          var id: Long = 0,
                          @ColumnInfo(name = "category_type")
                          var type: TransactionType,
                          @ColumnInfo(name = "category_name")
                          var name: String,
                          @ColumnInfo(name = "category_order")
                          var order: Int = 0,
                          @ColumnInfo(name = "category_icon_name")
                          var icon: String = "ic_category_unknown",
                          @ColumnInfo(name = "category_color_filter")
                          var color: Int = Color.CYAN,
                          @ColumnInfo(name = "category_internal_type")
                          var internalType: CategoryType = CategoryType.REGULAR,
                          @ColumnInfo(name = "category_is_internal")
                          var isInternal: Boolean = false) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CategoryEntity

        if (id != other.id) return false
        if (type != other.type) return false
        if (name != other.name) return false
        if (order != other.order) return false
        if (icon != other.icon) return false
        if (color != other.color) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + type.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + order
        result = 31 * result + icon.hashCode()
        result = 31 * result + color
        return result.toInt()
    }
}

