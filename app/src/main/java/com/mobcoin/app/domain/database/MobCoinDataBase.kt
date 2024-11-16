package com.mobcoin.app.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobcoin.app.domain.database.dao.CoinDao
import com.mobcoin.app.domain.database.dao.UserCoinDao
import com.mobcoin.app.domain.database.dao.UserDao
import com.mobcoin.app.domain.database.model.CoinData
import com.mobcoin.app.domain.database.model.User
import com.mobcoin.app.domain.database.model.UserCoin

@Database(entities = [User::class, CoinData::class, UserCoin::class], version = 2)
abstract class MobCoinDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun favoriteDao(): CoinDao
    abstract fun userCoinDao(): UserCoinDao
}