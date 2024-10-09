package com.mobcoin.app.domain.api

import com.mobcoin.app.model.FNGContainer
import retrofit2.Response
import retrofit2.http.GET

interface FNGService {

    @GET("fng")
    suspend fun getFNG(): Response<FNGContainer>

}