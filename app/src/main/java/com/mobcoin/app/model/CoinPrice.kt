package com.mobcoin.app.model

import com.google.gson.annotations.SerializedName

data class CoinPrice(
    @SerializedName("prices")val prices: List<List<Double>>
)