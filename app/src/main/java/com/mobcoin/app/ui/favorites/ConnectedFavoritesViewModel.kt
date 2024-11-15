package com.mobcoin.app.ui.favorites

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mobcoin.app.domain.FavoriteRepository
import com.mobcoin.app.domain.GeckoRepository
import com.mobcoin.app.model.Coin
import com.mobcoin.app.model.DetailedCoin
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ConnectedFavoritesViewModel() : ViewModel() {
    fun getFavoriteCoins(context: Context): LiveData<List<DetailedCoin>> {
        val liveData = MutableLiveData<List<DetailedCoin>>()

        viewModelScope.launch {
            FavoriteRepository.getFavorites(context).collect {
                val listCoins = it.map{ coinData ->
                    GeckoRepository.getCoinById(coinData.name).first().body()!!

                }
                liveData.postValue(listCoins)
            }
        }

        return liveData
    }
}