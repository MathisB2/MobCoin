package com.mobcoin.app.domain.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "t_user_x_coin", primaryKeys = ["idUser", "idCoin"])
data class UserCoin (
    @ColumnInfo(name = "id_user")
    val idUser: Long,

    @ColumnInfo(name = "id_coin")
    val idCoin: Long,
)