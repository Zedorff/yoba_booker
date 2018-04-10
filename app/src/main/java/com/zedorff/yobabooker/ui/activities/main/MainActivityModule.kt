package com.zedorff.yobabooker.ui.activities.main

import android.support.v7.app.AppCompatActivity
import com.zedorff.yobabooker.app.di.scopes.PerActivity
import com.zedorff.yobabooker.ui.activities.base.BaseActivityModule
import com.zedorff.yobabooker.ui.activities.main.fragments.accounts.AccountsFragment
import com.zedorff.yobabooker.ui.activities.main.fragments.accounts.AccountsFragmentModule
import com.zedorff.yobabooker.ui.activities.main.fragments.categories.CategoriesFragment
import com.zedorff.yobabooker.ui.activities.main.fragments.categories.CategoriesFragmentModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [(BaseActivityModule::class)])
abstract class MainActivityModule {
    @Binds
    @PerActivity
    internal abstract fun bindActivity(activity: MainActivity): AppCompatActivity

    @ContributesAndroidInjector(modules = [(AccountsFragmentModule::class)])
    abstract fun contributeAccountsFragment(): AccountsFragment

    @ContributesAndroidInjector(modules = [(CategoriesFragmentModule::class)])
    abstract fun contributeCategoriesFragment(): CategoriesFragment
}