package com.zedorff.yobabooker.model.db.embeded

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import com.zedorff.yobabooker.model.db.entities.AccountEntity
import com.zedorff.yobabooker.model.db.entities.TransactionEntity

class FullAccount {
    @Embedded
    lateinit var account: AccountEntity
    @Relation(parentColumn = "account_id", entityColumn = "account_id_relation")
    lateinit var transactions: List<TransactionEntity>
}