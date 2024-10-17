package com.mobcoin.app.domain.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.mobcoin.app.domain.database.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM T_USER WHERE email=:email AND password=:password")
    suspend fun getUser(email: String, password: String) : User

    @Query("")
    suspend fun getCoinsForUser(userId: Long) : User
}