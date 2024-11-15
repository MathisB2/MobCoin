package com.mobcoin.app.domain

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.mobcoin.app.domain.database.DBDataSource
import com.mobcoin.app.domain.database.model.User
import com.mobcoin.app.services.ImageService
import com.mobcoin.app.services.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.security.MessageDigest
import kotlin.math.log

object UserRepository {


    suspend fun createUser(username: String, email: String, password: String, bitmap: Bitmap?) : Flow<User> = flow{
        val user = User(
            surname = username,
            email =  email,
            password = hashPassword(password),
            profileImage = if (bitmap != null) ImageService.bitmapToByteArray(bitmap) else null
        )

        val id = DBDataSource.getDatabase().userDao().insert(user)
        emit(DBDataSource.getDatabase().userDao().getById(id.toInt())!!)
    }



    fun checkUserExists(email: String): Flow<Boolean> = flow {
        emit(DBDataSource.getDatabase().userDao().isUserExisting(email))
    }


    private fun hashPassword(password: String): String {
        val bytes = password.toByteArray()

        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)

        return digest.joinToString("") { "%02x".format(it) }
    }


    fun getCurrentUser(context: Context): Flow<User?> = flow {
        val currentUserId = UserService.getUserId(context)
        Log.d("currentId", currentUserId.toString())
        val user = DBDataSource.getDatabase().userDao().getById(currentUserId)
        emit(user)
    }



    fun login(user: User, context: Context): Flow<User?> = flow{
        Log.d("login", user.id.toString())
        UserService.saveUserId(context, user.id.toInt())
        emit(user)
    }

    fun logout(context: Context) {
        UserService.clearUserId(context)
    }


}