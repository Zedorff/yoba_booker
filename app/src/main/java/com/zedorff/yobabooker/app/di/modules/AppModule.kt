package com.zedorff.yobabooker.app.di.modules

import android.app.Application
import android.content.Context
import com.zedorff.yobabooker.app.YobaBooker
import com.zedorff.yobabooker.app.di.qualifiers.AppContext
import dagger.Binds
import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Module(includes = [(AndroidSupportInjectionModule::class), (DbModule::class),
    (ComponentsModule::class), (ViewModelModule::class), (RepositoryModule::class)])
abstract class AppModule {

    @Binds @Singleton
    abstract fun bindApplication(application: YobaBooker): Application

    @Binds @AppContext @Singleton
    abstract fun bindContext(application: YobaBooker): Context
}