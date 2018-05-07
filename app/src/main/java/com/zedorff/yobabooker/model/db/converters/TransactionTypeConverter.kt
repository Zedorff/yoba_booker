package com.zedorff.yobabooker.model.db.converters

import android.arch.persistence.room.TypeConverter
import com.zedorff.yobabooker.app.enums.TransactionType

class TransactionTypeConverter {
    @TypeConverter
    fun toTransactionType(value: Int): TransactionType {
        return TransactionType.from(value)
    }

    @TypeConverter
    fun toValue(type: TransactionType): Int {
        return type.value
    }
}