package com.mobcoin.app.domain.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface GenericDao<ENTITY> {
    @Insert
    suspend fun insert(entity: ENTITY) : Long

    @Insert
    suspend fun insertAll(entity: List<ENTITY>) :List<Long>

    @Update
    suspend fun update(entity: ENTITY)

    @Update
    suspend fun updateAll(entity: List<ENTITY>)

    @Delete
    suspend fun delete(entity: ENTITY)

    @Delete
    suspend fun deleteAll(entity: List<ENTITY>)
}