package com.zedorff.yobabooker.ui.activities.base

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.zedorff.yobabooker.app.di.qualifiers.ActivityContext
import com.zedorff.yobabooker.app.di.scopes.PerActivity
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class BaseActivityModule {
    @Binds @ActivityContext @PerActivity
    abstract fun bindContext(activity: AppCompatActivity): Context


    @Module
    companion object {
        @JvmStatic @Provides @PerActivity
        fun provideFragmentManager(activity: AppCompatActivity): FragmentManager {
            return activity.supportFragmentManager
        }
    }
}