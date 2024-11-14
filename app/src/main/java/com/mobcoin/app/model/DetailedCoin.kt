package com.mobcoin.app.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DetailedCoin (
    @SerializedName("id")val id: String,
    @SerializedName("symbol")val symbol: String,
    @SerializedName("name")val name: String,
    @SerializedName("image") val image: Map<String, String>? = null,
    @SerializedName("market_data") val marketData: CoinMarketData? = null,
    @SerializedName("market_cap_rank")val marketCapRank: Long,
) : Serializable {

    fun getImageUrlLarge(): String? {
        return image?.get("large")
    }
    fun getPriceByCurrency(currency: String): Double? {
        return marketData?.currentPrice?.get(currency)
    }
}

data class CoinMarketData(
    @SerializedName("current_price") val currentPrice: Map<String, Double>?,
    @SerializedName("price_change_percentage_24h") val percentagePriceChange24h: Double?,
    @SerializedName("price_change_percentage_7d")val percentagePriceChange7d: Double?,
    @SerializedName("price_change_percentage_14d")val percentagePriceChange14d: Double?,
    @SerializedName("price_change_percentage_30d")val percentagePriceChange30d: Double?,
    @SerializedName("price_change_percentage_60d")val percentagePriceChange60d: Double?,
    @SerializedName("price_change_percentage_200d")val percentagePriceChange200d: Double?,
    @SerializedName("price_change_percentage_1y")val percentagePriceChange1y: Double?,
    @SerializedName("price_change_percentage_1h_in_currency")val percentagePriceChange1h: Map<String, Double>?,
    @SerializedName("total_supply")val totalSupply: Double?,
    @SerializedName("market_cap") val marketCap: Map<String, Double>? = null,
    @SerializedName("ath") val ath: Map<String, Double>? = null,
    @SerializedName("atl") val atl: Map<String, Double>? = null,
){
    fun getPercentagePriceChange1hByCurrency(currency: String): Double? {
        return percentagePriceChange1h?.get(currency)
    }
    fun getAthByCurrency(currency: String): Double? {
        return ath?.get(currency)
    }
    fun getAtlByCurrency(currency: String): Double? {
        return atl?.get(currency)
    }
}