package com.zedorff.yobabooker.ui.activities.account

import androidx.appcompat.app.AppCompatActivity
import com.zedorff.yobabooker.app.di.scopes.PerActivity
import com.zedorff.yobabooker.ui.activities.account.fragments.AccountFragment
import com.zedorff.yobabooker.ui.activities.account.fragments.AccountFragmentModule
import com.zedorff.yobabooker.ui.activities.base.BaseActivityModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [(BaseActivityModule::class)])
abstract class AccountActivityModule {

    @Binds
    @PerActivity
    internal abstract fun bindActivity(activity: AccountActivity): AppCompatActivity

    @ContributesAndroidInjector(modules = [(AccountFragmentModule::class)])
    abstract fun contributeNewTransactionFragment(): AccountFragment
}