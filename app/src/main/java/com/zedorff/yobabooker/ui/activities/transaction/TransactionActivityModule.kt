package com.zedorff.yobabooker.ui.activities.transaction

import androidx.appcompat.app.AppCompatActivity
import com.zedorff.yobabooker.app.di.scopes.PerActivity
import com.zedorff.yobabooker.ui.activities.base.BaseActivityModule
import com.zedorff.yobabooker.ui.activities.transaction.fragments.TransactionFragment
import com.zedorff.yobabooker.ui.activities.transaction.fragments.TransactionFragmentModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [(BaseActivityModule::class)])
abstract class TransactionActivityModule {
    @Binds
    @PerActivity
    internal abstract fun bindActivity(activity: TransactionActivity): AppCompatActivity

    @ContributesAndroidInjector(modules = [(TransactionFragmentModule::class)])
    abstract fun contributeNewTransactionFragment(): TransactionFragment
}