package com.zedorff.yobabooker.model.db.dao

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Update

interface BaseDao<T> {

    @Insert
    fun insert(item: T): Long

    @Insert
    fun insert(vararg items: T)

    @Update
    fun update(item: T)

    @Delete
    fun delete(item: T)
}