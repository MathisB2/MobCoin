package com.mobcoin.app.domain.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "t_user_x_coin", primaryKeys = ["id_user", "id_coin"])
data class UserCoin (
    @ColumnInfo("id_user")
    val idUser: Long,

    @ColumnInfo("id_coin")
    val idCoin: Long,
)