package com.mobcoin.app.domain

import android.content.Context
import com.mobcoin.app.domain.database.DBDataSource
import com.mobcoin.app.domain.database.model.Asset
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object AssetRepository {

    fun getAssetWithCoinIdAndUserId(context: Context, coinId: String): Flow<Asset?> = flow {
        UserRepository.getCurrentUser(context).collect{
            if (it == null) throw Exception("unknown user")

            emit(DBDataSource.getDatabase().assetDao().getByCoinIdAndUserId(coinId,it.id))
        }
    }

    suspend fun checkAndUpdateQuantity(context: Context, idCoin: String, newQuantity: Double) {
        UserRepository.getCurrentUser(context).collect{
            if (it == null) throw Exception("unknown user")

            val existingAsset = DBDataSource.getDatabase().assetDao().getByCoinIdAndUserId(idCoin, it.id)

            if (existingAsset != null) {
                DBDataSource.getDatabase().assetDao().insertOrUpdate(existingAsset.copy(quantity = existingAsset.quantity + newQuantity))
            } else {
                DBDataSource.getDatabase().assetDao().insertOrUpdate(Asset(id = 0, coinId = idCoin, idUser = it.id, quantity = newQuantity))
            }
        }
    }

    suspend fun getAllAssetWithUserId(context: Context): Flow<List<Asset>> = flow{
        UserRepository.getCurrentUser(context).collect{
            if (it == null)
                throw Exception("unknown user")

            emit(DBDataSource.getDatabase().assetDao().getListByUserId(it.id))
        }
    }

}