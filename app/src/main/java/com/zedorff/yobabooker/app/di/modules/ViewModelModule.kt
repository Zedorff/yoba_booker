package com.zedorff.yobabooker.app.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.zedorff.yobabooker.app.di.annotations.ViewModelKey
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.ViewModelFactory
import com.zedorff.yobabooker.ui.activities.main.fragments.accounts.viewmodel.AccountViewModel
import com.zedorff.yobabooker.ui.activities.main.fragments.categories.viewmodel.CategoriesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(viewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    abstract fun bindCategoryViewModel(viewModel: CategoriesViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}