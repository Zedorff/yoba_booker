package com.zedorff.yobabooker.ui.activities.base.fragments

import android.content.Context
import android.support.v4.app.Fragment
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class BaseFragment<T: BaseViewModel>: Fragment() {
    @Inject lateinit var viewmodel: T

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}