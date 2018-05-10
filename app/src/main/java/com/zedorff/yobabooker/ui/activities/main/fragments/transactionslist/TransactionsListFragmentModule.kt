package com.zedorff.yobabooker.ui.activities.main.fragments.transactionslist

import androidx.fragment.app.Fragment
import com.zedorff.yobabooker.app.di.scopes.PerFragment
import dagger.Binds
import dagger.Module

@Module
abstract class TransactionsListFragmentModule {
    @Binds
    @PerFragment
    abstract fun bindFragment(fragment: TransactionsListFragment): Fragment
}