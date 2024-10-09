package com.mobcoin.app.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.mobcoin.app.R
import java.text.DecimalFormat
import kotlin.math.abs

class CoinUtils {
    companion object{
        private fun roundDoubleToTwoDecimals(value: Double?): String {
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
                    "▴${roundDoubleToTwoDecimals(value)}%"
                }
                else -> {
                    textView.setTextColor(ContextCompat.getColor(textView.context,R.color.md_theme_error))
                    "▾${roundDoubleToTwoDecimals(value)}%"
                }
            }
            textView.text = formattedValue
        }
    }
}