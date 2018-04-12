package com.zedorff.yobabooker.ui.activities.newaccount.fragments

import android.support.v4.app.Fragment
import com.zedorff.yobabooker.app.di.scopes.PerFragment
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragmentModule
import dagger.Binds
import dagger.Module

@Module(includes = [(BaseFragmentModule::class)])
abstract class NewAccountFragmentModule {
    @Binds
    @PerFragment
    abstract fun bindFragment(fragmentModule: NewAccountFragment): Fragment
}