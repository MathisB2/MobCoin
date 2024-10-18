package com.mobcoin.app.domain

import android.app.Application
import com.mobcoin.app.domain.database.DBDataSource
import com.mobcoin.app.domain.database.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.coroutines.coroutineContext

object UserRepository {
    private val userDao = DBDataSource.getDatabase().userDao()

    suspend fun getUser(email: String, password: String) : Flow<User> = flow {
        val user = userDao.getUser(email, hashPassword(password))
        emit(user)
    }

    private fun hashPassword(password: String): String {
        //TODO: hash password

        return password
    }
}