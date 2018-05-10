package com.zedorff.yobabooker.app.di.modules

import com.zedorff.yobabooker.app.di.scopes.PerActivity
import com.zedorff.yobabooker.ui.activities.account.AccountActivity
import com.zedorff.yobabooker.ui.activities.account.AccountActivityModule
import com.zedorff.yobabooker.ui.activities.main.MainActivity
import com.zedorff.yobabooker.ui.activities.main.MainActivityModule
import com.zedorff.yobabooker.ui.activities.transaction.TransactionActivity
import com.zedorff.yobabooker.ui.activities.transaction.TransactionActivityModule
import com.zedorff.yobabooker.ui.activities.transfer.TransferActivity
import com.zedorff.yobabooker.ui.activities.transfer.TransferActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ComponentsModule {

    //<editor-fold desc="Activities">

    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    @PerActivity
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [(TransactionActivityModule::class)])
    @PerActivity
    abstract fun contributeNewTransactionActivity(): TransactionActivity

    @ContributesAndroidInjector(modules = [(AccountActivityModule::class)])
    @PerActivity
    abstract fun contributeNewAccountActivity(): AccountActivity

    @ContributesAndroidInjector(modules = [TransferActivityModule::class])
    @PerActivity
    abstract fun contirbuteNewTransferActivity(): TransferActivity
    //</editor-fold>
}

