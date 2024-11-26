package com.mobcoin.app.domain.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.mobcoin.app.domain.database.model.CoinData

@Dao
interface CoinDao: GenericDao<CoinData> {
    @Query("SELECT * FROM T_COIN coin LEFT JOIN T_USER_X_COIN userCoin  ON coin.id = userCoin.id_coin WHERE id_user = :userId")
    suspend fun getCoinsFor(userId: Long): List<CoinData>


    @Query("SELECT * FROM T_COIN WHERE code = :code LIMIT 1")
    suspend fun getByCode(code: String): CoinData?

    @Query("SELECT * FROM T_COIN WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): CoinData?
}