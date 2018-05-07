package com.zedorff.yobabooker.model.db.entities

import android.arch.persistence.room.*
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.model.db.converters.TransactionTypeConverter

@Entity(tableName = "transactions", foreignKeys = [
    (ForeignKey(entity = CategoryEntity::class, parentColumns = [("category_id")], childColumns = [("category_id_relation")])),
    (ForeignKey(entity = AccountEntity::class, parentColumns = [("account_id")], childColumns = [("account_id_relation")], onDelete = ForeignKey.SET_NULL))
])
@TypeConverters(TransactionTypeConverter::class)
data class TransactionEntity(@PrimaryKey(autoGenerate = true)
                             @ColumnInfo(name = "transaction_id")
                             var id: Int = 0,
                             @ColumnInfo(name = "transaction_description")
                             var description: String? = null,
                             @ColumnInfo(name = "transaction_value")
                             var value: Float = 0f,
                             @ColumnInfo(name = "transaction_date")
                             var date: Long = 0L,
                             @ColumnInfo(name = "transaction_type")
                             var type: TransactionType = TransactionType.EMPTY,
                             @ColumnInfo(name = "account_id_relation")
                             var accountId: Int = 0,
                             @ColumnInfo(name = "category_id_relation")
                             var categoryId: Int = 0)