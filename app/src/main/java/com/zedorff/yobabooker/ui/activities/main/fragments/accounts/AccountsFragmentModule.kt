package com.zedorff.yobabooker.ui.activities.main.fragments.accounts

import android.support.v4.app.Fragment
import com.zedorff.yobabooker.app.di.scopes.PerFragment
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragmentModule
import com.zedorff.yobabooker.ui.activities.main.fragments.accounts.view.AccountsFragmentView
import dagger.Binds
import dagger.Module

@Module(includes = [(BaseFragmentModule::class)])
abstract class AccountsFragmentModule {

    @Binds @PerFragment
    abstract fun bindFragment(fragment: AccountsFragment): Fragment

    @Binds @PerFragment
    abstract fun bindView(fragment: AccountsFragment): AccountsFragmentView
}