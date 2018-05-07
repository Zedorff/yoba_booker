package com.zedorff.yobabooker.model.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.model.db.converters.TransactionTypeConverter
import com.zedorff.yobabooker.model.db.entities.CategoryEntity

@Dao
interface CategoryDao: BaseDao<CategoryEntity> {

    @Query("SELECT * FROM categories ORDER BY category_order")
    fun getAllCategories(): LiveData<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE category_id=:id")
    fun getCategory(id: String): LiveData<CategoryEntity>

    @TypeConverters(TransactionTypeConverter::class)
    @Query("SELECT * FROM categories WHERE category_type=:type ORDER BY category_order")
    fun getCategoriesByType(type: TransactionType): LiveData<List<CategoryEntity>>

    @Transaction
    @Update
    fun update(items: Collection<CategoryEntity>)
}