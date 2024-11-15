package com.mobcoin.app.domain

import com.mobcoin.app.domain.api.GeckoNetworkDataSource
import com.mobcoin.app.domain.httpQuery.MarketCoinsQuery
import com.mobcoin.app.model.Coin
import com.mobcoin.app.model.CoinPrice
import com.mobcoin.app.model.DetailedCoin
import com.mobcoin.app.model.GlobalMarketDataContainer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import kotlin.math.log


object GeckoRepository {

    suspend fun getCoins(marketCoinsQuery: MarketCoinsQuery): Flow<Response<List<Coin>>> = flow {
        emit(
            GeckoNetworkDataSource.apiService.getCoins("CG-soMb1tvkPUXUcqmJXvd3He5g", marketCoinsQuery.toMap())
        )
    }

    suspend fun getGlobalMarketData(): Flow<Response<GlobalMarketDataContainer>> = flow {
        emit(
            GeckoNetworkDataSource.apiService.getGlobalMarketData("CG-soMb1tvkPUXUcqmJXvd3He5g")
        )
    }

    suspend fun getCoinsPrices(coinId: String, currency: String, days: Int, precision: String? = null): Flow<Response<CoinPrice>> = flow {
        emit(
            GeckoNetworkDataSource.apiService.getCoinPrices(coinId, currency, days.toString(), precision, "CG-soMb1tvkPUXUcqmJXvd3He5g")
        )
    }

    suspend fun getCoinById(id: String): Flow<Response<DetailedCoin>> = flow {
        emit(
            GeckoNetworkDataSource.apiService.getCoinById(id,"CG-soMb1tvkPUXUcqmJXvd3He5g")
        )
    }

}