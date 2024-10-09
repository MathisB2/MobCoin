package com.mobcoin.app.model

import com.google.gson.annotations.SerializedName

data class FNG (
    @SerializedName("value")val value: Long,
    @SerializedName("value_classification")val valueClassification: String,
    @SerializedName("timestamp")val timestamp: Long,
    @SerializedName("time_until_update")val timeUntilUpdate: Long
    )