package com.mobcoin.app.domain.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.mobcoin.app.domain.database.model.UserCoin

@Dao
interface UserCoinDao: GenericDao<UserCoin> {

    @Query("SELECT COUNT(*) > 0 FROM T_USER_X_COIN WHERE id_user=:userId AND id_coin=:coinId LIMIT 1")
    suspend fun isCoinFavoriteForUser(userId: Long, coinId: Long): Boolean

}