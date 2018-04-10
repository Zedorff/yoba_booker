package com.zedorff.yobabooker.ui.activities.base.fragments

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


abstract class BaseFragment<VM: BaseViewModel>: Fragment() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: VM

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}