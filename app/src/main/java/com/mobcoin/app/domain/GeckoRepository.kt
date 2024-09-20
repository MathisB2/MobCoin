package com.mobcoin.app.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

object GeckoRepository {

    suspend fun getCoins() : Flow<Response<List<String>>> = flow {

    }

}