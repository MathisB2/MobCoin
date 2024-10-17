package com.mobcoin.app.domain.api

import com.mobcoin.app.model.Coin
import com.mobcoin.app.model.GlobalMarketDataContainer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
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
}