package com.mobcoin.app.domain.database.model

import android.graphics.drawable.Icon
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "t_asset")
data class Asset (
    @PrimaryKey(autoGenerate = true)
    var id : Long,
    val coinId : String,
    val quantity : Double,

    @ColumnInfo("id_user")
    val idUser: Long,
)