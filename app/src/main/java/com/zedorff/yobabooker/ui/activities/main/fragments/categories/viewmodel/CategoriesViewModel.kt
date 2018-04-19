package com.zedorff.yobabooker.ui.activities.main.fragments.categories.viewmodel

import android.arch.lifecycle.LiveData
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import com.zedorff.yobabooker.model.repository.YobaRepository
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(var repository: YobaRepository) : BaseViewModel() {
    private val categoriesLiveData: LiveData<List<CategoryEntity>> = repository.getAllCategories()

    fun getCategories() = categoriesLiveData
}