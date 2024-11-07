package com.mobcoin.app.model.search

import com.google.gson.annotations.SerializedName

data class SearchContainer (
    @SerializedName("coins")val coins: List<SearchCoin>
)