package com.zedorff.yobabooker.app.di.modules

import com.zedorff.yobabooker.app.di.scopes.PerActivity
import com.zedorff.yobabooker.ui.activities.main.MainActivity
import com.zedorff.yobabooker.ui.activities.main.MainActivityModule
import com.zedorff.yobabooker.ui.activities.newaccount.NewAccountActivity
import com.zedorff.yobabooker.ui.activities.newaccount.NewAccountActivityModule
import com.zedorff.yobabooker.ui.activities.newtransaction.NewTransactionActivity
import com.zedorff.yobabooker.ui.activities.newtransaction.NewTransactionActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ComponentsModule {

    //<editor-fold desc="Activities">

    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    @PerActivity
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [(NewTransactionActivityModule::class)])
    @PerActivity
    abstract fun contributeNewTransactionActivity(): NewTransactionActivity

    @ContributesAndroidInjector(modules = [(NewAccountActivityModule::class)])
    @PerActivity
    abstract fun contributeNewAccountActivity(): NewAccountActivity
    //</editor-fold>
}

