package com.mobcoin.app.ui.chart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import com.mobcoin.app.domain.GeckoRepository
import com.mobcoin.app.model.CoinPrice
import com.mobcoin.app.model.DetailedCoin
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


class ChartViewModel : ViewModel() {
    fun getCoinPrices(coinId: String, currency: String, days: Int, precision: String? = null): LiveData<List<Entry>> {
        val liveData = MutableLiveData<List<Entry>>()
        viewModelScope.launch {
            GeckoRepository.getCoinsPrices(coinId, currency, days, precision)
                .catch {
                    println(it)
                }
                .collect {
                    liveData.postValue(formatCoinToChartEntries(it.body()))
                }
        }

        return liveData
    }

    private fun formatCoinToChartEntries(coinPrices: CoinPrice?): List<Entry> {
        if (coinPrices == null) {
            return emptyList()
        }

        val entries = mutableListOf<Entry>()

        for (priceEntry in coinPrices.prices) {
            if (priceEntry.size == 2) {
                val value = priceEntry[0]
                val date = priceEntry[1]

                val entry = Entry(value.toFloat(), date.toFloat())

                entries.add(entry)
            }
        }

        return entries
    }

}