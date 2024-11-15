package com.mobcoin.app.domain.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.mobcoin.app.domain.database.model.CoinData
import retrofit2.http.GET

@Dao
interface CoinDao: GenericDao<CoinData> {
    @Query("SELECT * FROM T_COIN coin LEFT JOIN T_USER_X_COIN userCoin  ON coin.id = userCoin.id_coin WHERE id_user = :userId")
    suspend fun getCoinsFor(userId: Long): List<CoinData>
}