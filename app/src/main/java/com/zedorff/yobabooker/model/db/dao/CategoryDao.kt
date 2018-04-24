package com.zedorff.yobabooker.model.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.TypeConverters
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.model.db.converters.TransactionTypeConverter
import com.zedorff.yobabooker.model.db.entities.CategoryEntity

@Dao
interface CategoryDao: BaseDao<CategoryEntity> {

    @Query("SELECT * FROM categories")
    fun getAllCategories(): LiveData<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE category_id=:id")
    fun getCategory(id: String): LiveData<CategoryEntity>

    @TypeConverters(TransactionTypeConverter::class)
    @Query("SELECT * FROM categories WHERE category_type=:type")
    fun getCategoriesByType(type: TransactionType): LiveData<List<CategoryEntity>>

    @Query("SELECT * from categories WHERE category_type=0")
    fun getIncomeCategories(): LiveData<List<CategoryEntity>>

    @Query("SELECT * from categories WHERE category_type=1")
    fun getOutcomeCategories(): LiveData<List<CategoryEntity>>
}