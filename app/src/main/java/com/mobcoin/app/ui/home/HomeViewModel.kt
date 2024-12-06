package com.mobcoin.app.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobcoin.app.domain.FNGRepository
import com.mobcoin.app.domain.GeckoRepository
import com.mobcoin.app.domain.httpQuery.MarketCoinsQuery
import com.mobcoin.app.model.Coin
import com.mobcoin.app.model.FNG
import com.mobcoin.app.model.GlobalMarketData
import com.mobcoin.app.services.CurrencyService
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber

class HomeViewModel : ViewModel() {

    private var _coins : MutableLiveData<List<Coin>?> = MutableLiveData()
    val coins : LiveData<List<Coin>?> = _coins

    fun getFNG(): LiveData<FNG?>{
        val livedata = MutableLiveData<FNG?>()

        viewModelScope.launch {
            val data = FNGRepository.getFNG()
            data.catch { e ->
                Timber.e(e)
            }.collect { response ->
                if (response.isSuccessful) {
                    livedata.postValue(response.body()?.data?.first())
                } else {
                    livedata.postValue(null)
                }
            }

        }

        return livedata
    }

    fun fetchCoins(context: Context) {
        viewModelScope.launch {
            GeckoRepository.getCoins(MarketCoinsQuery(currency = CurrencyService.getCurrency(context)))
                .catch {
                    Timber.e(it)
                }
                .collect {
                    _coins.postValue(it.body())
                }
        }
    }

    fun getGlobalMarketData(): LiveData<GlobalMarketData?>{
        val livedata = MutableLiveData<GlobalMarketData?>()

        viewModelScope.launch {
            val data = GeckoRepository.getGlobalMarketData()
            data.catch { e ->
                Timber.e(e)
            }.collect { response ->
                if (response.isSuccessful) {
                    livedata.postValue(response.body()?.data)
                } else {
                    livedata.postValue(null)
                }
            }

        }

        return livedata
    }
}