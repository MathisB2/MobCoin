package com.mobcoin.app.domain.httpQuery

data class MarketCoinsQuery(
    val currency: String = "usd",
    val order: String = "market_cap_desc",
    val itemCount: Int = 100,
    val page: Int = 0,
    val priceChangePercentage: String = "1h,24h,7d,14d,30d,200d,1y",
    val sparkline: Boolean = false,
    val locale: String = "en",
    val precision: String = ""

){
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "vs_currency" to currency,
            "order" to order,
            "per_page" to itemCount,
            "price_change_percentage" to priceChangePercentage,
            "page" to page,
            "sparkline" to sparkline,
            "locale" to locale,
            "precision" to precision,
        )
    }
}