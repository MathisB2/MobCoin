package com.mobcoin.app.model

import android.graphics.Bitmap

data class FavoriteCoin(
    var id: String,
    var symbol: String,
    var name: String,
    var percentagePriceChange: Double,
    var imageUrl: String?,
    var fallbackImageBitmap: Bitmap?,
    var marketData: CoinMarketData?
)
