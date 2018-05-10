package com.zedorff.yobabooker.model.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface BaseDao<T> {

    @Insert
    fun insert(item: T): Long

    @Update
    fun update(item: T)

    @Delete
    fun delete(item: T)
}