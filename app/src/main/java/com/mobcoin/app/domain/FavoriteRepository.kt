package com.mobcoin.app.domain

import android.content.Context
import com.mobcoin.app.domain.database.DBDataSource
import com.mobcoin.app.domain.database.model.CoinData
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
}