package com.zedorff.yobabooker.model.db.embeded

import androidx.room.Embedded
import com.zedorff.yobabooker.model.db.entities.AccountEntity
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import com.zedorff.yobabooker.model.db.entities.TransactionEntity

class FullTransaction {
    @Embedded lateinit var account: AccountEntity
    @Embedded lateinit var category: CategoryEntity
    @Embedded lateinit var transaction: TransactionEntity
}