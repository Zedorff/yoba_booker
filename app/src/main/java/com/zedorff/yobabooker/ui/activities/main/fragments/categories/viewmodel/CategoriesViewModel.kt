package com.zedorff.yobabooker.ui.activities.main.fragments.categories.viewmodel

import com.zedorff.yobabooker.model.db.dao.CategoryDao
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(var categoryDao: CategoryDao) : BaseViewModel() {
    fun getCategories() = categoryDao.getAllCategories()
}