package com.zedorff.yobabooker.model.db

import android.content.res.Resources
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.enums.CategoryType
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.model.db.dao.CategoryDao
import com.zedorff.yobabooker.model.db.entities.CategoryEntity

class InitialWriter(resources: Resources, var categoryDao: CategoryDao) {

    private val external = mutableListOf<InitialCategory>()

    init {
        val categories = resources.getStringArray(R.array.categories)
        val categoryIcons = resources.getStringArray(R.array.categories_icons)
        val categoryColors = resources.getIntArray(R.array.categories_colors)
        val categoryTypes = resources.getIntArray(R.array.categories_type)
        val categoryInternalTypes = resources.getIntArray(R.array.categories_internal_type)

        categories.forEachIndexed { index, name ->
            external.add(object : InitialCategory() {
                override fun getName() = name
                override fun getType() = TransactionType.from(categoryTypes[index])
                override fun getIcon() = categoryIcons[index]
                override fun getColor() = categoryColors[index]
                override fun getInternalType() = CategoryType.from(categoryInternalTypes[index])
            })
        }
    }

    fun writeCategories() {
        external.forEach {
            categoryDao.insert(CategoryEntity(
                    name = it.getName(),
                    type = it.getType(),
                    icon = it.getIcon(),
                    color = it.getColor(),
                    internalType = it.getInternalType(),
                    isInternal = it.isInternal()
            ))
        }
    }

    private abstract class InitialCategory {
        abstract fun getName(): String
        abstract fun getType(): TransactionType
        abstract fun getIcon(): String
        abstract fun getColor(): Int
        abstract fun getInternalType(): CategoryType
        fun isInternal() = getInternalType() != CategoryType.REGULAR
    }
}