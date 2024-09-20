package com.mobcoin.app.api.managers

import com.mobcoin.app.api.services.CoinService

class CoinApiManager private constructor() : ApiManager<CoinService>("https://api.coingecko.com/api/v3/", CoinService::class.java) {
    companion object {
        @Volatile
        private var api: CoinApiManager? = null

        fun get() =
            api ?: synchronized(this) {
                api ?: CoinApiManager().also { api = it }
            }
        fun getService() = get().getService()
    }
}