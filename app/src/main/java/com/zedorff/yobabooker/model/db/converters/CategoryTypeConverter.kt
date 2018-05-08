package com.zedorff.yobabooker.model.db.converters

import android.arch.persistence.room.TypeConverter
import com.zedorff.yobabooker.app.enums.CategoryType
import com.zedorff.yobabooker.app.enums.TransactionType

class CategoryTypeConverter {
    @TypeConverter
    fun toCategoryType(value: Int): CategoryType {
        return CategoryType.from(value)
    }

    @TypeConverter
    fun toValue(type: CategoryType): Int {
        return type.value
    }
}