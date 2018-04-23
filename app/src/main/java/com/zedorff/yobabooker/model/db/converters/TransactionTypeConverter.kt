package com.zedorff.yobabooker.model.db.converters

import android.arch.persistence.room.TypeConverter
import com.zedorff.yobabooker.ui.activities.transaction.TransactionActivity

class TransactionTypeConverter {
    @TypeConverter
    fun toTransactionType(value: Int): TransactionActivity.TransactionType {
        return TransactionActivity.TransactionType.from(value)
    }

    @TypeConverter
    fun toValue(type: TransactionActivity.TransactionType): Int {
        return type.value
    }
}