package com.mobcoin.app.api

import retrofit2.http.Query

data class CoinQuery(
    val currency: String,
) {
    fun toMapQuery(): MutableMap<String, Any> {
        return mutableMapOf(
            "vs_currency" to currency,
        )
    }
}
