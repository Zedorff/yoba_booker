package com.zedorff.yobabooker.model.db.entities

import android.arch.persistence.room.*
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.model.db.converters.TransactionTypeConverter

@Entity(tableName = "transactions", foreignKeys = [
    (ForeignKey(entity = CategoryEntity::class, parentColumns = [("category_id")], childColumns = [("category_id_relation")])),
    (ForeignKey(entity = AccountEntity::class, parentColumns = [("account_id")], childColumns = [("account_id_relation")], onDelete = ForeignKey.SET_NULL)),
    (ForeignKey(entity = TransferEntity::class, parentColumns = [("transfer_id")], childColumns = [("transfer_id_relation")], onDelete = ForeignKey.CASCADE))
])
@TypeConverters(TransactionTypeConverter::class)
data class TransactionEntity(@PrimaryKey(autoGenerate = true)
                             @ColumnInfo(name = "transaction_id")
                             var id: Long = 0,
                             @ColumnInfo(name = "transaction_description")
                             var description: String? = null,
                             @ColumnInfo(name = "transaction_value")
                             var value: Float = 0f,
                             @ColumnInfo(name = "transaction_date")
                             var date: Long = System.currentTimeMillis(),
                             @ColumnInfo(name = "transaction_type")
                             var type: TransactionType = TransactionType.EMPTY,
                             @ColumnInfo(name = "account_id_relation")
                             var accountId: Long = 0,
                             @ColumnInfo(name = "category_id_relation")
                             var categoryId: Long = 0,
                             @ColumnInfo(name = "transfer_id_relation")
                             var transferId: Long? = null)