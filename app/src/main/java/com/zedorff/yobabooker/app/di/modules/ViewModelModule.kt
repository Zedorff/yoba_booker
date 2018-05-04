package com.zedorff.yobabooker.app.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.zedorff.yobabooker.app.di.annotations.ViewModelKey
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.ViewModelFactory
import com.zedorff.yobabooker.ui.activities.main.fragments.accountslist.viewmodel.AccountsListViewModel
import com.zedorff.yobabooker.ui.activities.main.fragments.categorieslist.viewmodel.CategoriesListViewModel
import com.zedorff.yobabooker.ui.activities.main.fragments.piechart.viewmodel.PieChartViewModel
import com.zedorff.yobabooker.ui.activities.main.fragments.transactionslist.viewmodel.TransactionsListViewModel
import com.zedorff.yobabooker.ui.activities.account.fragments.viewmodel.AccountViewModel
import com.zedorff.yobabooker.ui.activities.transaction.fragments.viewmodel.TransactionViewModel
import com.zedorff.yobabooker.ui.activities.transfer.fragments.viewmodel.TransferViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AccountsListViewModel::class)
    abstract fun bindAccountViewModel(viewModel: AccountsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesListViewModel::class)
    abstract fun bindCategoriesViewModel(viewModel: CategoriesListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransactionsListViewModel::class)
    abstract fun bindTransactionsViewModel(viewModel: TransactionsListViewModel): ViewModel

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
    @IntoMap
    @ViewModelKey(TransferViewModel::class)
    abstract fun bindTransferViewModel(viewMode: TransferViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}