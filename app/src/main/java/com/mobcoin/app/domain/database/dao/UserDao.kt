package com.mobcoin.app.domain.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.mobcoin.app.domain.database.model.CoinData
import com.mobcoin.app.domain.database.model.User

@Dao
interface UserDao: GenericDao<User> {
    @Query("SELECT * FROM T_USER WHERE email=:email AND password=:hashedPassword")
    suspend fun getUser(email: String, hashedPassword: String) : User

    @Query("SELECT * from T_COIN coin LEFT JOIN T_USER_X_COIN userCoin ON coin.id = userCoin.id_coin LEFT JOIN T_USER user ON user.id = userCoin.id_user WHERE id_user = :userId")
    suspend fun getCoinsForUser(userId: Long) : Array<CoinData>
}