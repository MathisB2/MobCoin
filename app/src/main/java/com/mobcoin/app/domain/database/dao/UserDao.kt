package com.mobcoin.app.domain.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.mobcoin.app.domain.database.model.User

@Dao
interface UserDao: GenericDao<User> {
    @Query("SELECT * FROM T_USER WHERE id=:id LIMIT 1")
    suspend fun getById(id: Int) : User?

    @Query("SELECT * FROM T_USER WHERE email=:email LIMIT 1")
    suspend fun getByEmail(email: String) : User?

    @Query("SELECT COUNT(*) > 0 FROM T_USER WHERE email = :email")
    suspend fun isUserExisting(email: String): Boolean
}