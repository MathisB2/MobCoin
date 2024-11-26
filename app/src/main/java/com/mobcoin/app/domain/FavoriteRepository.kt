package com.mobcoin.app.domain

import android.content.Context
import com.mobcoin.app.domain.database.DBDataSource
import com.mobcoin.app.domain.database.model.CoinData
import com.mobcoin.app.domain.database.model.UserCoin
import com.mobcoin.app.model.DetailedCoin
import com.mobcoin.app.services.ImageService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

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

    suspend fun isFavorite(coin: DetailedCoin, context: Context): Flow<Boolean> = flow {
        UserRepository.getCurrentUser(context).catch {
            emit(false)
        }.collect{ user ->
            if(user == null)
                emit(false)
            else {
                val coinData = DBDataSource.getDatabase().favoriteDao().getByCode(coin.symbol)
                if(coinData == null)
                    emit(false)
                else
                    emit(DBDataSource.getDatabase().userCoinDao().isCoinFavoriteForUser(user.id, coinData.id))
            }
        }
    }


    suspend fun setFavorite(coin: DetailedCoin, context: Context) {
        UserRepository.getCurrentUser(context).collect{ user ->

            // ensure user is logged in
            if(user == null)
                throw Exception("User not logged in")


            var coinData = DBDataSource.getDatabase().favoriteDao().getByCode(coin.symbol)

            // add coin to database if it doesn't exist
            if(coinData == null){
                val insertedCoinId = DBDataSource.getDatabase().favoriteDao().insert(CoinData(
                    geckoId = coin.id,
                    name = coin.name,
                    code = coin.symbol,
                    icon = withContext(Dispatchers.IO) { ImageService.bitmapToByteArray(Picasso.get().load(coin.getImageUrlSmall()).get()) },
                ))
                coinData = DBDataSource.getDatabase().favoriteDao().getById(insertedCoinId)
            }

            if(coinData == null)
                throw Exception("Error inserting coin")


            // check if coin is already in user's favorites
            if(DBDataSource.getDatabase().userCoinDao().isCoinFavoriteForUser(user.id, coinData.id))
                throw Exception("Coin already in favorites")

            // add coin to user's favorites
            DBDataSource.getDatabase().userCoinDao().insert(UserCoin(user.id, coinData.id))

        }
    }


    suspend fun removeFavorite(coin: DetailedCoin, context: Context) {
        UserRepository.getCurrentUser(context).collect{ user ->
            // ensure user is logged in
            if(user == null)
                throw Exception("User not logged in")


            val coinData = DBDataSource.getDatabase().favoriteDao().getByCode(coin.symbol)

            // ensure coin exists
            if(coinData == null)
                throw Exception("Coin not found")

            // ensure coin is in user's favorites
            if(!DBDataSource.getDatabase().userCoinDao().isCoinFavoriteForUser(user.id, coinData.id))
                throw Exception("Coin not in favorites")

            // remove coin from user's favorites
            DBDataSource.getDatabase().userCoinDao().delete(UserCoin(user.id, coinData.id))

        }
    }
}