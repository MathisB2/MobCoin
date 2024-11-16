package com.mobcoin.app.domain

import android.content.Context
import com.mobcoin.app.domain.database.DBDataSource
import com.mobcoin.app.domain.database.model.CoinData
import com.mobcoin.app.domain.database.model.UserCoin
import com.mobcoin.app.model.Coin
import com.mobcoin.app.services.ImageService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

object FavoriteRepository {
    suspend fun getFavorites(context: Context): Flow<List<CoinData>> = flow{
        UserRepository.getCurrentUser(context).catch {
            emit(emptyList())
        }.collect{ user ->
            if(user != null)
                emit(DBDataSource.getDatabase().favoriteDao().getCoinsFor(user.id))
            else
                emit(emptyList())
        }
    }


    suspend fun setFavorite(coin: Coin, context: Context) {
        UserRepository.getCurrentUser(context).collect{ user ->
            if(user == null)
                throw Exception("User not logged in")


            var coinData = DBDataSource.getDatabase().favoriteDao().getByCode(coin.symbol)

            if(coinData == null){
                val insertedCoinId = DBDataSource.getDatabase().favoriteDao().insert(CoinData(
                    name = coin.name,
                    code = coin.symbol,
                    icon = ImageService.bitmapToByteArray(Picasso.get().load(coin.image).get()),
                ))
                coinData = DBDataSource.getDatabase().favoriteDao().getById(insertedCoinId)
            }

            if(coinData == null)
                throw Exception("Error inserting coin")

            DBDataSource.getDatabase().userCoinDao().insert(UserCoin(user.id, coinData.id))

        }
    }
}