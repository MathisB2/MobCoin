package com.mobcoin.app.domain.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobcoin.app.domain.database.model.Asset

@Dao
interface AssetDao: GenericDao<Asset> {

    @Query("SELECT * FROM T_ASSET WHERE coinId=:id AND id_user=:userId LIMIT 1")
    suspend fun getByCoinIdAndUserId(id: String, userId: Long) : Asset?

    @Query("SELECT * FROM T_ASSET WHERE id_user = :userId")
    suspend fun getListByUserId(userId: Long) : List<Asset>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(asset: Asset)

}