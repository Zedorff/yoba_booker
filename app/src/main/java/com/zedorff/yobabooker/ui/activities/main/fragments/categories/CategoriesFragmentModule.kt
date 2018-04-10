package com.zedorff.yobabooker.ui.activities.main.fragments.categories

import android.support.v4.app.Fragment
import com.zedorff.yobabooker.app.di.scopes.PerFragment
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragmentModule
import com.zedorff.yobabooker.ui.activities.main.fragments.categories.view.CategoriesFragmentView
import dagger.Binds
import dagger.Module

@Module(includes = [(BaseFragmentModule::class)])
abstract class CategoriesFragmentModule {

    @Binds
    @PerFragment
    abstract fun bindFragment(fragment: CategoriesFragment): Fragment

    @Binds
    @PerFragment
    abstract fun bindView(fragment: CategoriesFragment): CategoriesFragmentView
}