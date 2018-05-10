package com.zedorff.yobabooker.ui.activities.main.fragments.accountslist

import androidx.fragment.app.Fragment
import com.zedorff.yobabooker.app.di.scopes.PerFragment
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragmentModule
import dagger.Binds
import dagger.Module

@Module(includes = [(BaseFragmentModule::class)])
abstract class AccountsListFragmentModule {

    @Binds @PerFragment
    abstract fun bindFragment(fragment: AccountsListFragment): Fragment
}