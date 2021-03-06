package com.zedorff.yobabooker.app.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.zedorff.yobabooker.app.di.annotations.ViewModelKey
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.ViewModelFactory
import com.zedorff.yobabooker.ui.activities.main.fragments.accounts.viewmodel.AccountsViewModel
import com.zedorff.yobabooker.ui.activities.main.fragments.categories.viewmodel.CategoriesViewModel
import com.zedorff.yobabooker.ui.activities.main.fragments.piechart.viewmodel.PieChartViewModel
import com.zedorff.yobabooker.ui.activities.main.fragments.transactions.viewmodel.TransactionsViewModel
import com.zedorff.yobabooker.ui.activities.newaccount.fragments.viewmodel.NewAccountViewModel
import com.zedorff.yobabooker.ui.activities.newtransaction.fragments.viewmodel.NewTransactionViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AccountsViewModel::class)
    abstract fun bindAccountViewModel(viewModel: AccountsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    abstract fun bindCategoriesViewModel(viewModel: CategoriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransactionsViewModel::class)
    abstract fun bindTransactionsViewModel(viewModel: TransactionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewTransactionViewModel::class)
    abstract fun bindNewTransactionViewModel(viewModel: NewTransactionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewAccountViewModel::class)
    abstract fun bindNewAccountViewModel(viewModel: NewAccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PieChartViewModel::class)
    abstract fun bindPieChartViewModel(viewModel: PieChartViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}