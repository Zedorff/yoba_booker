package com.zedorff.yobabooker.ui.activities.main.view

import com.zedorff.yobabooker.model.db.entities.AccountEntity
import com.zedorff.yobabooker.model.db.entities.CategoryEntity

interface MainActivityView {
    fun openTransactionsForCategory(category: CategoryEntity)
    fun openTransactionsForAccount(account: AccountEntity)
}