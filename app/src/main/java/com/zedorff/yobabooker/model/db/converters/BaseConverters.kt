package com.zedorff.yobabooker.model.db.converters

import androidx.room.TypeConverter
import com.zedorff.yobabooker.app.extensions.boolean
import com.zedorff.yobabooker.app.extensions.int

class BaseConverters {
    @TypeConverter
    fun fromBoolean(value: Boolean): Int{
        return value.int
    }

    @TypeConverter
    fun toBoolean(value: Int): Boolean {
        return value.boolean
    }
}