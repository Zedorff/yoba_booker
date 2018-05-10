package com.zedorff.yobabooker.ui.activities.main.fragments.categorieslist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import com.zedorff.yobabooker.model.repository.YobaRepository
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import javax.inject.Inject

class CategoriesListViewModel @Inject constructor(var repository: YobaRepository) : BaseViewModel() {
    private val categoriesLiveData: LiveData<List<CategoryEntity>>
    private val categoryType: MutableLiveData<TransactionType> = MutableLiveData()

    init {
        categoriesLiveData = Transformations.switchMap(categoryType, {
            repository.loadCategoriesByType(it)
        })
    }

    fun setCategoryType(type: TransactionType) {
        categoryType.value = type
    }

    fun getCategories() = categoriesLiveData
    fun getCategoryType(): TransactionType = categoryType.value!!

    fun updateCategoriesOrder(items: List<CategoryEntity>) {
        items.forEachIndexed { index, categoryEntity ->
            categoryEntity.order = index
        }
        repository.updateCategories(items)
    }
}