package com.zedorff.yobabooker.app;

import android.app.Activity
import android.app.Application
import android.app.Service
import com.facebook.stetho.Stetho
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.di.DaggerAppComponent
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.app.managers.AppPreferencesManager
import com.zedorff.yobabooker.model.db.dao.CategoryDao
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import kotlinx.coroutines.experimental.async
import javax.inject.Inject

class YobaBooker : Application(), HasActivityInjector, HasServiceInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var serviceInjector: DispatchingAndroidInjector<Service>
    @Inject
    lateinit var appPreference: AppPreferencesManager
    @Inject
    lateinit var categoryDao: CategoryDao

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        DaggerAppComponent.builder()
                .create(this)
                .inject(this)
        if (!appPreference.isInitialDataWritten()) {
            async {
                val incomeColors = resources.getIntArray(R.array.categories_income_color)
                val outcomeColors = resources.getIntArray(R.array.categories_outcome_colors)
                val incomeDrawable = resources.getStringArray(R.array.categories_income_icons)
                val outcomeDrawables = resources.getStringArray(R.array.categories_outcome_icons)

                val incomeCategories = resources.getStringArray(R.array.categories_income)
                val outcomeCategories = resources.getStringArray(R.array.categories_outcome)

                incomeCategories.forEachIndexed { index, name ->
                    categoryDao.insert(CategoryEntity(
                            name = name,
                            type = TransactionType.INCOME,
                            order = index,
                            icon = incomeDrawable[index],
                            color = incomeColors[index]
                    ))
                }

                outcomeCategories.forEachIndexed { index, name ->
                    categoryDao.insert(CategoryEntity(
                            name = name,
                            type = TransactionType.OUTCOME,
                            order = index,
                            icon = outcomeDrawables[index],
                            color = outcomeColors[index]
                    ))
                }
                appPreference.storeInitialDataWritten()
            }
        }
    }

    override fun serviceInjector(): AndroidInjector<Service> = serviceInjector
    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}
