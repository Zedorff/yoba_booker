package com.zedorff.yobabooker.model.db.embeded

import androidx.room.Embedded
import androidx.room.Relation
import com.zedorff.yobabooker.model.db.entities.AccountEntity
import com.zedorff.yobabooker.model.db.entities.TransactionEntity

class FullAccount {
    @Embedded
    lateinit var account: AccountEntity
    @Relation(parentColumn = "account_id", entityColumn = "account_id_relation")
    lateinit var transactions: List<TransactionEntity>
}