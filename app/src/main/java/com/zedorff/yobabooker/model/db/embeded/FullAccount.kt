package com.zedorff.yobabooker.model.db.embeded

import android.accounts.Account
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import com.zedorff.yobabooker.model.db.entities.AccountEntity
import com.zedorff.yobabooker.model.db.entities.TransactionEntity

class FullAccount {
    @Embedded
    lateinit var account: AccountEntity
    @Relation(parentColumn = "id", entityColumn = "account_id")
    lateinit var transactions: List<TransactionEntity>
}