package com.mobcoin.app.ui.chart

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HourValueFormatter: ValueFormatter() {
    private val mFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)

    override fun getFormattedValue(value: Float): String {
        val millis = value.toLong()
        return mFormat.format(Date(millis))
    }
}