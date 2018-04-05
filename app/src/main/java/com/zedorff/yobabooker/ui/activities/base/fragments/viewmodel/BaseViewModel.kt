package com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel: ViewModel() {
    private val compositeDisposable: CompositeDisposable by lazy {CompositeDisposable()}
}