package com.mobcoin.app.api.services

import com.mobcoin.app.api.modele.Coin
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinService {
    @GET("coins/markets")
    fun coinList(@Query("vs_currency") currency: String): Call<List<Coin>>
}