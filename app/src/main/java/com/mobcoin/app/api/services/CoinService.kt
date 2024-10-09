package com.mobcoin.app.api.services

import com.mobcoin.app.api.CoinQuery
import com.mobcoin.app.api.modele.Coin
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface CoinService {
    @GET("coins/markets")
    fun coinList( @QueryMap coinQuery: MutableMap<String, Any>): Call<List<Coin>>
}