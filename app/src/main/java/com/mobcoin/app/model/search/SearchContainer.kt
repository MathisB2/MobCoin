package com.mobcoin.app.model.search

import com.google.gson.annotations.SerializedName

data class SearchContainer (
    @SerializedName("coins")val coins: List<SearchCoin>
)

data class TrendingContainer (
    @SerializedName("coins")val coins: List<TrendingItemContainer>
){
    fun getTrendingCoins(): List<SearchCoin> {
        return coins.map { coin ->
            coin.coin
        }
    }
}

data class TrendingItemContainer (
    @SerializedName("item")val coin: SearchCoin
)