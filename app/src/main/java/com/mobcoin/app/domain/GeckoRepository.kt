package com.mobcoin.app.domain

import com.mobcoin.app.domain.api.GeckoNetworkDataSource
import com.mobcoin.app.domain.httpQuery.MarketCoinsQuery
import com.mobcoin.app.model.Coin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response


object GeckoRepository {

    suspend fun getCoins(marketCoinsQuery: MarketCoinsQuery): Flow<Response<List<Coin>>> = flow {
        emit(
            GeckoNetworkDataSource.apiService.getCoins("CG-soMb1tvkPUXUcqmJXvd3He5g", marketCoinsQuery.toMap())
        )
    }

}