package com.mobcoin.app.ui.chart

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class DateValueFormatter: ValueFormatter() {
    private val mFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)

    override fun getFormattedValue(value: Float): String {
        val millis = TimeUnit.HOURS.toMillis(value.toLong())
        return mFormat.format(Date(millis))
    }
}