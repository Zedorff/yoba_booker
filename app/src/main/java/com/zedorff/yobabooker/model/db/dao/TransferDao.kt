package com.zedorff.yobabooker.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.zedorff.yobabooker.model.db.embeded.FullTransfer
import com.zedorff.yobabooker.model.db.entities.TransferEntity

@Dao
interface TransferDao: BaseDao<TransferEntity> {

    @Query("SELECT * FROM transfers WHERE transfer_id=:id")
    fun loadTransfer(id: Long): LiveData<TransferEntity>

    @Query("SELECT * FROM transfers WHERE transfer_id=:id")
    fun loadFullTransfer(id: Long): LiveData<FullTransfer>

    @Query("DELETE FROM transfers WHERE transfer_id=:id")
    fun delete(id: Long)
}