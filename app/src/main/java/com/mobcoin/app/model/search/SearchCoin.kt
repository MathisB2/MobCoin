package com.mobcoin.app.model.search

import com.google.gson.annotations.SerializedName

data class SearchCoin (
    @SerializedName("id")val id: String,
    @SerializedName("name")val name: String,
    @SerializedName("api_symbol")val apiSymbol: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("market_cap_rank") val marketCapRank: Long,
    @SerializedName("large")val coinIcon: String
)