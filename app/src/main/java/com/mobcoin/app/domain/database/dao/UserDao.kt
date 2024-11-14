package com.mobcoin.app.domain.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.mobcoin.app.domain.database.model.CoinData
import com.mobcoin.app.domain.database.model.User

@Dao
interface UserDao: GenericDao<User> {
    @Query("SELECT * FROM T_USER WHERE id=:id LIMIT 1")
    suspend fun getById(id: Int) : User?

    @Query("SELECT * FROM T_USER WHERE email=:email LIMIT 1")
    suspend fun getByEmail(email: String) : User?

    @Query("SELECT COUNT(*) > 0 FROM T_USER WHERE email = :email")
    suspend fun isUserExisting(email: String): Boolean

    @Query("SELECT * from T_COIN coin LEFT JOIN T_USER_X_COIN userCoin ON coin.id = userCoin.id_coin LEFT JOIN T_USER user ON user.id = userCoin.id_user WHERE id_user = :userId")
    suspend fun getCoinsForUser(userId: Long) : Array<CoinData>
}