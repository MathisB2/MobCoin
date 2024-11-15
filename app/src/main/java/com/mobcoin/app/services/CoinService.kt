package com.mobcoin.app.services

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.mobcoin.app.R
import java.text.DecimalFormat
import kotlin.math.abs

object CoinService {
    fun roundDoubleToTwoDecimals(value: Double?): String {
        if(value == null) return "0"
        val decimalFormat = DecimalFormat("0.00")
        return decimalFormat.format(abs(value))
    }

    fun setPercentageText(value: Double?, textView: TextView) {
        val formattedValue = when {
            value == null || value == 0.0 -> {
                textView.setTextColor(ContextCompat.getColor(textView.context,R.color.md_theme_secondary))
                "0.00%"
            }
            value > 0 -> {
                textView.setTextColor(ContextCompat.getColor(textView.context,R.color.colorSuccess))
                "▴ ${roundDoubleToTwoDecimals(value)}%"
            }
            else -> {
                textView.setTextColor(ContextCompat.getColor(textView.context,R.color.md_theme_error))
                "▾ ${roundDoubleToTwoDecimals(value)}%"
            }
        }
        textView.text = formattedValue
    }

    fun formatNumber(number: Double?): String {
        return when {
            number == null -> "0"
            number >= 1_000_000_000_000 -> "${roundDoubleToTwoDecimals(number / 1_000_000_000_000)} T"
            number >= 1_000_000_000 -> "${roundDoubleToTwoDecimals(number / 1_000_000_000)} B"
            number >= 1_000_000 -> "${roundDoubleToTwoDecimals(number / 1_000_000)} M"
            number >= 1_000 -> "${roundDoubleToTwoDecimals(number / 1_000)} K"
            else -> number.toString()
        }
    }
}