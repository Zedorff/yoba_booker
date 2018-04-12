package com.zedorff.yobabooker.ui.activities.newaccount

import android.support.v7.app.AppCompatActivity
import com.zedorff.yobabooker.app.di.scopes.PerActivity
import com.zedorff.yobabooker.ui.activities.base.BaseActivityModule
import com.zedorff.yobabooker.ui.activities.newaccount.fragments.NewAccountFragment
import com.zedorff.yobabooker.ui.activities.newaccount.fragments.NewAccountFragmentModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [(BaseActivityModule::class)])
abstract class NewAccountActivityModule {

    @Binds
    @PerActivity
    internal abstract fun bindActivity(activity: NewAccountActivity): AppCompatActivity

    @ContributesAndroidInjector(modules = [(NewAccountFragmentModule::class)])
    abstract fun contributeNewTransactionFragment(): NewAccountFragment
}