package com.mobcoin.app.domain.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.mobcoin.app.domain.database.model.Asset
import com.mobcoin.app.domain.database.model.CoinData
import com.mobcoin.app.domain.database.model.User

@Dao
interface AssetDao: GenericDao<Asset> {

    @Query("SELECT * FROM T_ASSET WHERE coinId=:id AND id_user=:userId LIMIT 1")
    suspend fun getByCoinId(id: String, userId: String) : Asset?

    @Query("SELECT * FROM T_ASSET WHERE id_user = :userId")
    suspend fun getListByUserId(userId: String) : List<Asset>

}