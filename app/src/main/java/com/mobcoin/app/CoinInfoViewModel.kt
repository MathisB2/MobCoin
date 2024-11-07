package com.mobcoin.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobcoin.app.domain.GeckoRepository
import com.mobcoin.app.model.CoinPrice
import com.mobcoin.app.model.DetailedCoin
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryOf
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber


class CoinInfoViewModel : ViewModel() {
    val coinChart1EntryModelProducer = ChartEntryModelProducer()

    fun fetchCoinPrices(coinId: String, currency: String, days: String, precision: String? = null) {
        viewModelScope.launch {
            GeckoRepository.getCoinsPrices(coinId, currency, days, precision)
                .catch {
                    Timber.e(it)
                }
                .collect {
                    updateCoinChartData(it.body())
                }
        }
    }

    private fun updateCoinChartData(coinPrices: CoinPrice?) {
        if (coinPrices != null) {
            val entries = mutableListOf<ChartEntry>()

            for (priceEntry in coinPrices.prices) {
                if (priceEntry.size == 2) {
                    val value = priceEntry[0].toFloat()
                    val date = priceEntry[1].toFloat()

                    val entry = entryOf(value, date)

                    entries.add(entry)
                }
            }

            coinChart1EntryModelProducer.setEntries(entries)
        } else {
            Timber.e("No coin price data available to update the chart.")
        }
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