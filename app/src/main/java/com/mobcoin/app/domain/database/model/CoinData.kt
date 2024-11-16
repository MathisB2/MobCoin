package com.mobcoin.app.domain.database.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "t_coin", indices = [Index(value = ["code"], unique = true)])
data class CoinData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    val code: String,
    val name: String,
    val icon: ByteArray,
)