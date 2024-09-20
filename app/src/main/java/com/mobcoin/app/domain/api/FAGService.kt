package com.mobcoin.app.domain.api

import com.mobcoin.app.model.Coin
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface FAGService {

    @GET("fng")
    suspend fun getFearAndGreedIndex(): Response<List<Coin>>

}