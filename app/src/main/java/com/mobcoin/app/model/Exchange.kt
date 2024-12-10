package com.mobcoin.app.model

import com.google.gson.annotations.SerializedName

data class Exchange(
    @SerializedName("name")val name: String,
    @SerializedName("image")val image: String
)