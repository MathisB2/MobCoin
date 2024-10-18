package com.mobcoin.app.domain.database.dao

import androidx.room.Dao
import com.mobcoin.app.domain.database.model.CoinData

@Dao
interface CoinDao: GenericDao<CoinData> {

}