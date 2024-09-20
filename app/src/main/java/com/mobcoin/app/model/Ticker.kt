package com.mobcoin.app.model

data class Ticker(
    val base: String,
    val target: String,
    val volume: Float,
    val lastPrice: Float,
    val exchange: Exchange
)