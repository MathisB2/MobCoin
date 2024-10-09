package com.mobcoin.app.domain

import com.mobcoin.app.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

object UserRepository {
    suspend fun getUser(email: String, password: String) : Flow<User> = flow {
        //val user = todo
        val user = User("testUser", "test@gmail.com", "1234", null)
        emit(user)
    }
}