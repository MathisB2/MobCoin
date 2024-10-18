package com.mobcoin.app.model

import com.google.gson.annotations.SerializedName

data class GlobalMarketData (
    @SerializedName("active_cryptocurrencies") val activeCoinsCount: Int,
    @SerializedName("markets") val marketsCount: Int,
    @SerializedName("total_market_cap") val totalMarketCap: Map<String, Double>,
    @SerializedName("total_volume") val totalVolume: Map<String, Double>,
    @SerializedName("market_cap_percentage") val marketCapPercentage: Map<String, Double>,
    @SerializedName("market_cap_change_percentage_24h_usd") val marketCapChangePercentage24hUsd: Double,
)