package com.zedorff.yobabooker.ui.activities.main.fragments.piechart

import androidx.fragment.app.Fragment
import com.zedorff.yobabooker.app.di.scopes.PerFragment
import dagger.Binds
import dagger.Module

@Module
abstract class PieChartFragmentModule {
    @Binds
    @PerFragment
    abstract fun bindFragment(fragment: PieChartFragment): Fragment
}