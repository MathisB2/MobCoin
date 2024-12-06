package com.mobcoin.app.services

import android.content.Context
import android.content.SharedPreferences

object CurrencyService {

    private const val PREF_NAME = "AppPreferences"
    private const val KEY_LANGUAGE = "currency"

    fun getCurrency(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_LANGUAGE, "usd") ?: "usd"
    }

    fun setCurrency(context: Context, currencyCode: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(KEY_LANGUAGE, currencyCode)
        editor.apply()
    }

}