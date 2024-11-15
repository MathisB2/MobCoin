package com.mobcoin.app.domain

import android.content.Context
import android.graphics.Bitmap
import com.mobcoin.app.domain.database.DBDataSource
import com.mobcoin.app.domain.database.model.User
import com.mobcoin.app.services.ImageService
import com.mobcoin.app.services.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object UserRepository {


    suspend fun createUser(username: String, email: String, password: String, bitmap: Bitmap?) : Flow<User> = flow{
        val user = User(
            surname = username,
            email =  email,
            password = UserService.hashPassword(password),
            profileImage = if (bitmap != null) ImageService.bitmapToByteArray(bitmap) else null
        )

        val id = DBDataSource.getDatabase().userDao().insert(user)
        emit(DBDataSource.getDatabase().userDao().getById(id.toInt())!!)
    }



    fun checkUserExists(email: String): Flow<Boolean> = flow {
        emit(DBDataSource.getDatabase().userDao().isUserExisting(email))
    }





    fun getCurrentUser(context: Context): Flow<User?> = flow {
        val currentUserId = UserService.getUserId(context)
        val user = DBDataSource.getDatabase().userDao().getById(currentUserId)
        emit(user)
    }



    fun login(user: User, context: Context){
        UserService.saveUserId(context, user.id.toInt())
    }

    fun logout(context: Context) {
        UserService.clearUserId(context)
    }

    fun getUserByEmail(email: String): Flow<User?> = flow {
        emit(DBDataSource.getDatabase().userDao().getByEmail(email))
    }


}