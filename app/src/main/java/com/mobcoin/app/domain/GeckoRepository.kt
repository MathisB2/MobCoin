package com.mobcoin.app.domain


import com.mobcoin.app.domain.api.GeckoNetworkDataSource
import com.mobcoin.app.domain.httpQuery.MarketCoinsQuery
import com.mobcoin.app.model.Coin
import com.mobcoin.app.model.CoinPrice
import com.mobcoin.app.model.DetailedCoin
import com.mobcoin.app.model.GlobalMarketDataContainer
import com.mobcoin.app.model.search.SearchContainer
import com.mobcoin.app.model.search.TrendingContainer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response


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

    suspend fun searchCoin(query: String): Flow<Response<SearchContainer>> = flow {
        emit(
            GeckoNetworkDataSource.apiService.searchCoin("CG-soMb1tvkPUXUcqmJXvd3He5g",query)
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

    suspend fun getTrendingCoins(): Flow<Response<TrendingContainer>> = flow{
        println("test")
        emit(
            GeckoNetworkDataSource.apiService.getTrendingCoins("CG-soMb1tvkPUXUcqmJXvd3He5g")
        )
    }
}