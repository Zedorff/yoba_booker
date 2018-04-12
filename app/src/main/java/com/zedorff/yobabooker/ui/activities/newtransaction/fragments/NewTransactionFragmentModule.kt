package com.zedorff.yobabooker.ui.activities.newtransaction.fragments

import android.support.v4.app.Fragment
import com.zedorff.yobabooker.app.di.scopes.PerFragment
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragmentModule
import dagger.Binds
import dagger.Module

@Module(includes = [(BaseFragmentModule::class)])
abstract class NewTransactionFragmentModule {
    @Binds
    @PerFragment
    abstract fun bindFragment(fragment: NewTransactionFragment): Fragment
}