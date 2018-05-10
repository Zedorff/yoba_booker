package com.zedorff.yobabooker.ui.activities.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

@SuppressLint("Registered")
open class BaseActivity: AppCompatActivity(), HasSupportFragmentInjector {

    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    protected fun replaceFragment(@IdRes container: Int, fragment: Fragment) {
        fragmentManager.beginTransaction()
                .replace(container, fragment)
                .addToBackStack(null)
                .commit()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector
}