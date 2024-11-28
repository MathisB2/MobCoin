package com.mobcoin.app.model

data class DisplayedAsset (
    val coinId : String,
    val quantity : Double,
    val coinName : String,
    val coinSymbol : String,
    val coinPrice : Double,
    val coinChange : Double,
    val coinIcon : String
)