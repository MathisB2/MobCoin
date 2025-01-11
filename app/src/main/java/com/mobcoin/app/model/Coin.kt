package com.mobcoin.app.model

import com.google.gson.annotations.SerializedName

data class Coin (
    @SerializedName("id")val id: String,
    @SerializedName("symbol")val symbol: String,
    @SerializedName("name")val name: String,
    @SerializedName("image")val image: String,
    @SerializedName("current_price") val currentPrice: Double,
    @SerializedName("market_cap") val marketCap: Double,
    @SerializedName("market_cap_rank")val marketCapRank: Double,
    @SerializedName("total_volume")val volume: Double,
    @SerializedName("price_change_percentage_24h_in_currency") val percentagePriceChange24h: Double?,
    @SerializedName("price_change_percentage_1h_in_currency")val percentagePriceChange1h: Double?,
    @SerializedName("price_change_percentage_200d_in_currency")val percentagePriceChange200d: Double?,
    @SerializedName("price_change_percentage_14d_in_currency")val percentagePriceChange14d: Double?,
    @SerializedName("price_change_percentage_7d_in_currency")val percentagePriceChange7d: Double?,
    @SerializedName("price_change_percentage_30d_in_currency")val percentagePriceChange30d: Double?,
    @SerializedName("price_change_percentage_1y_in_currency")val percentagePriceChange1y: Double?,
    @SerializedName("ath")val ath: Double,
    @SerializedName("atl")val atl: Double
)