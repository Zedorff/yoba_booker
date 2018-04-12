package com.zedorff.yobabooker.app.di.modules

import com.zedorff.yobabooker.model.repository.YobaRepository
import com.zedorff.yobabooker.model.repository.YobaRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindYobaRepository(repository: YobaRepositoryImpl): YobaRepository
}