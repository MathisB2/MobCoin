package com.mobcoin.app.model

import android.graphics.Bitmap

data class User(
    val name: String,
    val email: String,
    val password: String,
    val avatar: Bitmap?
)
