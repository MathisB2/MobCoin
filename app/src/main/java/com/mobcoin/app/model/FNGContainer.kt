package com.mobcoin.app.model

import com.google.gson.annotations.SerializedName

data class FNGContainer (
    @SerializedName("data")val data: List<FNG>
)