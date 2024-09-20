package com.mobcoin.app.domain.api

import com.mobcoin.app.api.modele.Coin
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeckoService {
    @GET("coins/markets")
    suspend fun getCoins(@Query("vs_currency") currency: String,
                         @Query("order") order: String?,
                         @Query("per_page") itemCount: Int?,
                         @Query("page") page: Int?,
                         @Query("price_change_percentage") priceChangePercentage: Int?,
                         @Query("sparkline") sparkline: Boolean?,
                         @Query("locale") locale: String?,
                         @Query("precision") precision: String?): Response<List<Coin>>

}