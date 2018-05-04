package com.zedorff.yobabooker.ui.activities.transfer

import android.support.v7.app.AppCompatActivity
import com.zedorff.yobabooker.app.di.scopes.PerActivity
import com.zedorff.yobabooker.ui.activities.base.BaseActivityModule
import com.zedorff.yobabooker.ui.activities.transfer.fragments.TransferFragment
import com.zedorff.yobabooker.ui.activities.transfer.fragments.TransferFragmentModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [(BaseActivityModule::class)])
abstract class TransferActivityModule {
    @Binds
    @PerActivity
    internal abstract fun bindActivity(activity: TransferActivity): AppCompatActivity

    @ContributesAndroidInjector(modules = [(TransferFragmentModule::class)])
    abstract fun contributeTransferFragment(): TransferFragment
}