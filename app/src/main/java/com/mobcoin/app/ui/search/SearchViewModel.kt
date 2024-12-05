package com.mobcoin.app.ui.search


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobcoin.app.domain.GeckoRepository
import com.mobcoin.app.model.search.SearchCoin
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchViewModel : ViewModel(){

    private var _searchCoins : MutableLiveData<List<SearchCoin>?> = MutableLiveData()
    val searchCoins : LiveData<List<SearchCoin>?> = _searchCoins

    private var _trendingCoins : MutableLiveData<List<SearchCoin>?> = MutableLiveData()
    val trendingCoins : LiveData<List<SearchCoin>?> = _trendingCoins

    fun fetchSearchCoins(query: String) {
        viewModelScope.launch {
            GeckoRepository.searchCoin(query)
                .catch {
                    Timber.e(it)
                }
                .collect {
                    _searchCoins.postValue(it.body()?.coins)
                }
        }
    }

    fun fetchTrendingCoins() {
        viewModelScope.launch {
            GeckoRepository.getTrendingCoins()
                .catch {
                    println(it)
                }
                .collect {
                    _trendingCoins.postValue(it.body()?.getTrendingCoins())
                }
        }
    }

}