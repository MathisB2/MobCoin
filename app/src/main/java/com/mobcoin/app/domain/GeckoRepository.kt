package com.mobcoin.app.domain

import com.mobcoin.app.BuildConfig
import com.mobcoin.app.domain.api.GeckoNetworkDataSource
import com.mobcoin.app.domain.httpQuery.MarketCoinsQuery
import com.mobcoin.app.model.Coin
import com.mobcoin.app.model.CoinPrice
import com.mobcoin.app.model.GlobalMarketDataContainer
import com.mobcoin.app.model.search.SearchContainer
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
            GeckoNetworkDataSource.apiService.getCoins(BuildConfig.API_KEY, marketCoinsQuery.toMap())
        )
    }

    suspend fun getGlobalMarketData(): Flow<Response<GlobalMarketDataContainer>> = flow {
        emit(
            GeckoNetworkDataSource.apiService.getGlobalMarketData(BuildConfig.API_KEY)
        )
    }

    suspend fun searchCoin(query: String): Flow<Response<SearchContainer>> = flow {
        emit(
            GeckoNetworkDataSource.apiService.searchCoin(BuildConfig.API_KEY,query)
        )
    }

    suspend fun getCoinsPrices(coinId: String, currency: String, days: String, precision: String? = null): Flow<Response<CoinPrice>> = flow {
        emit(
            GeckoNetworkDataSource.apiService.getCoinPrices(coinId, currency, days, precision, "CG-soMb1tvkPUXUcqmJXvd3He5g")
        )
    }

}