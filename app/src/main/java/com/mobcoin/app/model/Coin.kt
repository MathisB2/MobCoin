package com.mobcoin.app.model

data class Coin(
    val symbol: String,
    val name: String,
    val image: String,
    val currentPrice: Float,
    val marketCap: Int,
    val marketCapRank: Int,
    val volume: Int,
    val percentagePriceChange24h: Float,
    val percentagePriceChange7d: Float,
    val percentagePriceChange30d: Float,
    val percentagePriceChange90d: Float,
    val percentagePriceChange1y: Float,
    val ath: Float,
    val atl: Float,
    val tickers: List<Ticker>
)