package com.mobcoin.app.ui

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


class CoinInfoViewModel : ViewModel() {
    fun getCoinPrices(coinId: String, currency: String, days: String, precision: String? = null): LiveData<List<Entry>> {
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
                val value = priceEntry[0].toFloat()
                val date = priceEntry[1].toFloat()

                val entry = Entry(value, date)

                entries.add(entry)
            }
        }

        return entries
    }

    fun getCoinById(id: String): LiveData<DetailedCoin?>{
        val livedata = MutableLiveData<DetailedCoin?>()

        viewModelScope.launch {
            val data = GeckoRepository.getCoinById(id)
            data.catch { e ->
                println(e.message)
            }.collect { response ->
                livedata.postValue(response.body())
            }

        }

        return livedata
    }

}