package com.mobcoin.app.domain.database.dao

import androidx.room.Dao
import com.mobcoin.app.domain.database.model.UserCoin

@Dao
interface UserCoinDao: GenericDao<UserCoin> {
}