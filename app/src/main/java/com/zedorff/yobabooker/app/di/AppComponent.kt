package com.zedorff.yobabooker.app.di

import com.zedorff.yobabooker.app.YobaBooker
import com.zedorff.yobabooker.app.di.modules.AppModule
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent: AndroidInjector<YobaBooker> {

    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<YobaBooker>()
}