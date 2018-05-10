package com.zedorff.yobabooker.model.db.converters

import androidx.room.TypeConverter
import com.zedorff.yobabooker.app.enums.CategoryType

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