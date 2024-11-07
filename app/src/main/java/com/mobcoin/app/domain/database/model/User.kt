package com.mobcoin.app.domain.database.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "t_user", indices = [Index(value = ["email"], unique = true, )])
data class User (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    val surname: String,
    val email: String,
    val password: String
)