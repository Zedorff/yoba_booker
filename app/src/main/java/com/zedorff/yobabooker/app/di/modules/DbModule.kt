package com.zedorff.yobabooker.app.di.modules

import android.content.Context
import androidx.room.Room
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.di.qualifiers.AppContext
import com.zedorff.yobabooker.model.db.AppDatabase
import com.zedorff.yobabooker.model.db.Migrations
import com.zedorff.yobabooker.model.db.dao.AccountDao
import com.zedorff.yobabooker.model.db.dao.CategoryDao
import com.zedorff.yobabooker.model.db.dao.TransactionDao
import com.zedorff.yobabooker.model.db.dao.TransferDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun provideAppDataBase(@AppContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, context.getString(R.string.app_name))
                .addMigrations(Migrations.MIGRATION_FROM_1_TO_2)
                .addMigrations(Migrations.MIGRATION_FROM_2_TO_3)
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides @Singleton
    fun provideAccountDao(appDatabase: AppDatabase): AccountDao = appDatabase.accountDao()

    @Provides @Singleton
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao = appDatabase.categoryDao()

    @Provides @Singleton
    fun provideTransactionDao(appDatabase: AppDatabase): TransactionDao = appDatabase.transactionDao()

    @Provides @Singleton
    fun provideTransferDao(appDatabase: AppDatabase): TransferDao = appDatabase.transferDao()

}