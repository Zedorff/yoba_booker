package com.zedorff.yobabooker.model.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.zedorff.yobabooker.model.db.entities.CategoryEntity

@Dao
interface CategoryDao: BaseDao<CategoryEntity> {

    @Query("SELECT * FROM categories")
    fun getAllCategories(): LiveData<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE id=:id")
    fun getCategory(id: String): LiveData<CategoryEntity>

    @Query("SELECT * from categories WHERE type=0")
    fun getIncomeCategories(): LiveData<List<CategoryEntity>>

    @Query("SELECT * from categories WHERE type=1")
    fun getOutcomeCategories(): LiveData<List<CategoryEntity>>
}