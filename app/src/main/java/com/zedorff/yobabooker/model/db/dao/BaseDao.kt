package com.zedorff.yobabooker.model.db.dao

import android.arch.persistence.room.*

@Dao
interface BaseDao<T> {

    @Insert
    fun insert(item: T): Long

    @Update
    fun update(item: T)

    @Delete
    fun delete(item: T)
}