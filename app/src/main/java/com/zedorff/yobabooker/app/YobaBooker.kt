package com.zedorff.yobabooker.app;

import android.app.Activity
import android.app.Application
import android.app.Service
import com.facebook.stetho.Stetho
import com.zedorff.yobabooker.app.di.DaggerAppComponent
import com.zedorff.yobabooker.app.managers.AppPreferencesManager
import com.zedorff.yobabooker.model.db.InitialWriter
import com.zedorff.yobabooker.model.db.dao.CategoryDao
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
                writeInitData()
                appPreference.storeInitialDataWritten()
            }
        }
    }

    private fun writeInitData() {
        val write = InitialWriter(resources, categoryDao)
        write.writeCategories()
    }

    override fun serviceInjector(): AndroidInjector<Service> = serviceInjector
    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}
