package com.zedorff.yobabooker.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zedorff.yobabooker.app.enums.CategoryType
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.model.db.converters.CategoryTypeConverter
import com.zedorff.yobabooker.model.db.converters.TransactionTypeConverter
import com.zedorff.yobabooker.model.db.entities.CategoryEntity

@Dao
@TypeConverters(TransactionTypeConverter::class, CategoryTypeConverter::class)
interface CategoryDao: BaseDao<CategoryEntity> {

    @Query("SELECT * FROM categories WHERE category_is_internal=0 ORDER BY category_order")
    fun loadAllCategories(): LiveData<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE category_id=:id")
    fun loadCategory(id: Long): LiveData<CategoryEntity>

    @TypeConverters(TransactionTypeConverter::class)
    @Query("SELECT * FROM categories WHERE category_type=:type AND category_is_internal=0 ORDER BY category_order")
    fun loadCategoriesByType(type: TransactionType): LiveData<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE category_internal_type=:type")
    fun loadCategoryByInternalType(type: CategoryType): LiveData<CategoryEntity>

    @Transaction
    @Update
    fun update(items: Collection<CategoryEntity>)
}