package com.zedorff.yobabooker.ui.activities.base.fragments

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.support.v4.app.Fragment
import com.zedorff.dragandswiperecycler.helper.SDHelperListener
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


abstract class BaseFragment<VM: BaseViewModel>: Fragment(), SDHelperListener {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: VM

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun dragDropEnabled() = false
    override fun swipeEnabled() = false
    override fun onDragged(from: Int, to: Int) {}
    override fun onDragDropEnded() {}
    override fun onSwiped(position: Int) {}
}