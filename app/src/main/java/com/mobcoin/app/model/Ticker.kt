package com.mobcoin.app.model

import com.google.gson.annotations.SerializedName

data class Ticker(
    @SerializedName("base")val base: String,
    @SerializedName("target")val target: String,
    @SerializedName("volume")val volume: Float,
    @SerializedName("last")val lastPrice: Float,
    @SerializedName("market")val exchange: TickerMarket,
    @SerializedName("trade_url")val url: String
)
data class TickerMarket(
    @SerializedName("name")val name: String,
    @SerializedName("identifier")val identifier: String,
    var image: String
)