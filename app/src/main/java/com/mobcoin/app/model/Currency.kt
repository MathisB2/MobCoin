package com.mobcoin.app.model

enum class Currency(val code: String, val symbol: String) {
    USD("usd", "$"),
    EUR("eur", "€"),
    JPY("jpy", "¥");

    companion object {
        fun getCurrencySymbole(code: String): String{
            for (currency in entries) {
                if(currency.code.equals(code)){
                    return currency.symbol
                }
            }
            throw IllegalArgumentException("Unsupported language name: $code")
        }
    }
}

