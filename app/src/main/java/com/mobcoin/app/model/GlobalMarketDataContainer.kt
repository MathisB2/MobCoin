package com.mobcoin.app.model

import com.google.gson.annotations.SerializedName

data class GlobalMarketDataContainer (
    @SerializedName("data")val data: GlobalMarketData
)