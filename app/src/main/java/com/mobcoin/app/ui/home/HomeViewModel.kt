package com.mobcoin.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobcoin.app.domain.FNGRepository
import com.mobcoin.app.domain.GeckoRepository
import com.mobcoin.app.domain.httpQuery.MarketCoinsQuery
import com.mobcoin.app.model.Coin
import com.mobcoin.app.model.FNG
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber

class HomeViewModel : ViewModel() {

    private var _fng : MutableLiveData<FNG?> = MutableLiveData()
    val fng : LiveData<FNG?> = _fng

    private var _coins : MutableLiveData<List<Coin>?> = MutableLiveData()
    val coins : LiveData<List<Coin>?> = _coins

    fun fetchFNG() {
        viewModelScope.launch {
            FNGRepository.getFNG()
                .catch {
                    Timber.e(it)
                }
                .collect {
                    val data = it.body()?.data

                    if(!data.isNullOrEmpty()){
                        _fng.postValue(it.body()?.data?.first())
                    }
                }
        }
    }

    fun fetchCoins() {
        viewModelScope.launch {
            GeckoRepository.getCoins(MarketCoinsQuery())
                .catch {
                    Timber.e(it)
                }
                .collect {
                    _coins.postValue(it.body())
                }
        }
    }

}