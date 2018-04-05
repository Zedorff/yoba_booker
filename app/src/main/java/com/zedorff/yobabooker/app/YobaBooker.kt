package com.zedorff.yobabooker.app;

import android.app.Activity
import android.app.Application
import android.app.Service
import com.facebook.stetho.Stetho;
import com.zedorff.yobabooker.app.di.DaggerAppComponent
import dagger.android.*
import javax.inject.Inject

class YobaBooker: Application(), HasActivityInjector, HasServiceInjector {

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var serviceInjector: DispatchingAndroidInjector<Service>

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        DaggerAppComponent.builder()
                .create(this)
                .inject(this)
    }

    override fun serviceInjector(): AndroidInjector<Service> = serviceInjector
    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}
