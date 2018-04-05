package com.zedorff.yobabooker.ui.activities.main

import android.support.v7.app.AppCompatActivity
import com.zedorff.yobabooker.app.di.scopes.PerActivity
import com.zedorff.yobabooker.ui.activities.base.BaseActivityModule
import dagger.Binds
import dagger.Module

@Module(includes = [(BaseActivityModule::class)])
abstract class MainActivityModule {
    @Binds
    @PerActivity
    internal abstract fun bindActivity(activity: MainActivity): AppCompatActivity
}