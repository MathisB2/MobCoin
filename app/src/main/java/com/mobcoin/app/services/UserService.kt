package com.mobcoin.app.services

import android.content.Context
import android.content.SharedPreferences
import java.security.MessageDigest


object UserService {
    private val userIdKey = "USER_ID"

    fun saveUserId(context: Context, userId: Int) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MobCoinPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(userIdKey, userId)
        editor.apply()
    }

    fun getUserId(context: Context): Int {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MobCoinPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getInt(userIdKey, -1)
    }

    fun clearUserId(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MobCoinPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(userIdKey)
        editor.apply()
    }


    fun hashPassword(password: String): String {
        val bytes = password.toByteArray()

        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)

        return digest.joinToString("") { "%02x".format(it) }
    }


}