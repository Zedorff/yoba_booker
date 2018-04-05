package com.zedorff.yobabooker.app.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.di.qualifiers.AppContext
import com.zedorff.yobabooker.model.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun provideAppDataBase(@AppContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, context.getString(R.string.app_name))
                .fallbackToDestructiveMigration()
                .build()
    }
}