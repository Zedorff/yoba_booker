package com.zedorff.yobabooker.ui.activities.newtransaction

import android.support.v7.app.AppCompatActivity
import com.zedorff.yobabooker.app.di.scopes.PerActivity
import com.zedorff.yobabooker.ui.activities.base.BaseActivityModule
import com.zedorff.yobabooker.ui.activities.newtransaction.fragments.NewTransactionFragment
import com.zedorff.yobabooker.ui.activities.newtransaction.fragments.NewTransactionFragmentModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [(BaseActivityModule::class)])
abstract class NewTransactionActivityModule {
    @Binds
    @PerActivity
    internal abstract fun bindActivity(activity: NewTransactionActivity): AppCompatActivity

    @ContributesAndroidInjector(modules = [(NewTransactionFragmentModule::class)])
    abstract fun contributeNewTransactionFragment(): NewTransactionFragment
}