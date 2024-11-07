package com.mobcoin.app.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobcoin.app.domain.GeckoRepository
import com.mobcoin.app.domain.httpQuery.MarketCoinsQuery
import com.mobcoin.app.model.Coin
import com.mobcoin.app.model.search.SearchCoin
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchViewModel : ViewModel(){

    private var _searchCoins : MutableLiveData<List<SearchCoin>?> = MutableLiveData()
    val searchCoins : LiveData<List<SearchCoin>?> = _searchCoins

    fun fetchSearchCoins(query: String) {
        viewModelScope.launch {
            GeckoRepository.searchCoin(query)
                .catch {
                    Timber.e(it)
                }
                .collect {
                    Log.e("TAAAG", query)
                    Log.e("TAAAG", it.body().toString())
                    _searchCoins.postValue(it.body()?.coins)
                }
        }
    }
}