package com.mobcoin.app.domain.database.model

import android.graphics.drawable.Icon
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "t_asset")
data class Asset (
    @PrimaryKey(autoGenerate = true)
    var id : String,
    var quantity : Double

)