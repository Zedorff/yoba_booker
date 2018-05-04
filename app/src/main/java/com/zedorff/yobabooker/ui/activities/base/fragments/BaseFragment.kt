package com.zedorff.yobabooker.ui.activities.base.fragments

import android.app.DatePickerDialog
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.extensions.getDayOfMonth
import com.zedorff.yobabooker.app.extensions.getMonth
import com.zedorff.yobabooker.app.extensions.getYear
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import dagger.android.support.AndroidSupportInjection
import java.util.*
import javax.inject.Inject


abstract class BaseFragment<VM: BaseViewModel>: Fragment() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: VM

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}