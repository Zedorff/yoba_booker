package com.zedorff.yobabooker.app.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.zedorff.yobabooker.app.di.annotations.ViewModelKey
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.ViewModelFactory
import com.zedorff.yobabooker.ui.activities.main.fragments.accounts.viewmodel.AccountsViewModel
import com.zedorff.yobabooker.ui.activities.main.fragments.categories.viewmodel.CategoriesViewModel
import com.zedorff.yobabooker.ui.activities.main.fragments.piechart.viewmodel.PieChartViewModel
import com.zedorff.yobabooker.ui.activities.main.fragments.transactions.viewmodel.TransactionsViewModel
import com.zedorff.yobabooker.ui.activities.account.fragments.viewmodel.AccountViewModel
import com.zedorff.yobabooker.ui.activities.transaction.fragments.viewmodel.TransactionViewModel
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
    @ViewModelKey(TransactionViewModel::class)
    abstract fun bindNewTransactionViewModel(viewModel: TransactionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindNewAccountViewModel(viewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PieChartViewModel::class)
    abstract fun bindPieChartViewModel(viewModel: PieChartViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}