package com.mobcoin.app.domain.api

import com.mobcoin.app.model.Coin
import com.mobcoin.app.model.CoinPrice
import com.mobcoin.app.model.DetailedCoin
import com.mobcoin.app.model.GlobalMarketDataContainer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface GeckoService {

    @GET("coins/markets")
    suspend fun getCoins(
        @Header("x-cg-demo-api-key") apiKey: String,
        @QueryMap marketCoinsQuery: MutableMap<String, Any>
    ): Response<List<Coin>>

    @GET("global")
    suspend fun getGlobalMarketData(
        @Header("x-cg-demo-api-key") apiKey: String
    ): Response<GlobalMarketDataContainer>

    @GET("coins/{id}/market_chart")
    suspend fun getCoinPrices(
        @Path("id") coinId: String,
        @Query("vs_currency") currency: String,
        @Query("days") days: String,
        @Query("precision") precision: String? = null,
        @Header("x-cg-demo-api-key") apiKey: String
    ): Response<CoinPrice>

    @GET("coins/{id}")
    suspend fun getCoinById(
        @Path("id") coinId: String,
        @Header("x-cg-demo-api-key") apiKey: String,
    ): Response<DetailedCoin>
}
