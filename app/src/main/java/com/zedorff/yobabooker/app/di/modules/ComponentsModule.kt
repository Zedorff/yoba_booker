package com.zedorff.yobabooker.app.di.modules

import com.zedorff.yobabooker.app.di.scopes.PerActivity
import com.zedorff.yobabooker.ui.activities.main.MainActivity
import com.zedorff.yobabooker.ui.activities.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ComponentsModule {

    //<editor-fold desc="Activities">

    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    @PerActivity
    abstract fun contributeMainActivity(): MainActivity
    //</editor-fold>
}

