package com.zedorff.yobabooker.model.db.embeded

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.model.db.entities.TransactionEntity
import com.zedorff.yobabooker.model.db.entities.TransferEntity

class FullTransfer {
    @Embedded
    var transfer: TransferEntity = TransferEntity()
    @Relation(parentColumn = "transfer_id", entityColumn = "transfer_id_relation")
    var transactions: List<TransactionEntity> = mutableListOf(
            TransactionEntity(type = TransactionType.OUTCOME),
            TransactionEntity(type = TransactionType.INCOME)
    )
}