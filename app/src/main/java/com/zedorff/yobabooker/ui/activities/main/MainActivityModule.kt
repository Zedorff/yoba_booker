package com.zedorff.yobabooker.ui.activities.main

import androidx.appcompat.app.AppCompatActivity
import com.zedorff.yobabooker.app.di.scopes.PerActivity
import com.zedorff.yobabooker.ui.activities.base.BaseActivityModule
import com.zedorff.yobabooker.ui.activities.main.fragments.accountslist.AccountsListFragment
import com.zedorff.yobabooker.ui.activities.main.fragments.accountslist.AccountsListFragmentModule
import com.zedorff.yobabooker.ui.activities.main.fragments.categorieslist.CategoriesListFragment
import com.zedorff.yobabooker.ui.activities.main.fragments.categorieslist.CategoriesListFragmentModule
import com.zedorff.yobabooker.ui.activities.main.fragments.piechart.PieChartFragment
import com.zedorff.yobabooker.ui.activities.main.fragments.piechart.PieChartFragmentModule
import com.zedorff.yobabooker.ui.activities.main.fragments.transactionslist.TransactionsListFragment
import com.zedorff.yobabooker.ui.activities.main.fragments.transactionslist.TransactionsListFragmentModule
import com.zedorff.yobabooker.ui.activities.main.view.MainActivityView
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [(BaseActivityModule::class)])
abstract class MainActivityModule {
    @Binds
    @PerActivity
    abstract fun bindActivity(activity: MainActivity): AppCompatActivity

    @Binds
    @PerActivity
    abstract fun bindActivityView(activity: MainActivity): MainActivityView

    @ContributesAndroidInjector(modules = [(AccountsListFragmentModule::class)])
    abstract fun contributeAccountsFragment(): AccountsListFragment

    @ContributesAndroidInjector(modules = [(CategoriesListFragmentModule::class)])
    abstract fun contributeCategoriesFragment(): CategoriesListFragment

    @ContributesAndroidInjector(modules = [(TransactionsListFragmentModule::class)])
    abstract fun contributeTransactionsFragment(): TransactionsListFragment

    @ContributesAndroidInjector(modules = [(PieChartFragmentModule::class)])
    abstract fun contributePieChartFragment(): PieChartFragment
}