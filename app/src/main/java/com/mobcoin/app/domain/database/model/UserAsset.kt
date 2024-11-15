package com.mobcoin.app.domain.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "t_user_x_asset", primaryKeys = ["id_user", "id_asset"])

data class UserAsset (
    @ColumnInfo("id_user")
    val idUser: Long,

    @ColumnInfo("id_asset")
    val idAsset: String,
)