package com.mobcoin.app.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mobcoin.app.domain.database.dao.AssetDao
import com.mobcoin.app.domain.database.dao.CoinDao
import com.mobcoin.app.domain.database.dao.UserDao
import com.mobcoin.app.domain.database.model.Asset
import com.mobcoin.app.domain.database.model.CoinData
import com.mobcoin.app.domain.database.model.User
import com.mobcoin.app.domain.database.model.UserCoin

@Database(entities = [User::class, CoinData::class, UserCoin::class, Asset::class], version = 2)
abstract class MobCoinDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun favoriteDao(): CoinDao
    abstract fun assetDao(): AssetDao
}