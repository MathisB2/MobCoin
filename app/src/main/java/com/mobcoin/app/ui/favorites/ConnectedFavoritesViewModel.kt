package com.mobcoin.app.ui.favorites

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobcoin.app.domain.FavoriteRepository
import com.mobcoin.app.domain.GeckoRepository
import com.mobcoin.app.model.DetailedCoin
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ConnectedFavoritesViewModel : ViewModel() {
    fun getFavoriteCoins(context: Context): LiveData<List<DetailedCoin>> {
        val liveData = MutableLiveData<List<DetailedCoin>>()

        viewModelScope.launch {
            FavoriteRepository.getFavorites(context).collect {
                val listCoins = ArrayList<DetailedCoin>()

                it.forEach { coinData ->
                    val response = GeckoRepository.getCoinById(coinData.geckoId)
                    response.catch { error ->
                        Log.e("ConnectedFavoritesViewModel", error.message.toString())
                    }.collect{ response ->
                        if (response.isSuccessful)
                            response.body()?.let { detailedCoin ->
                                listCoins.add(detailedCoin)
                            }
                        else
                            Log.e("ConnectedFavoritesViewModel", "Error fetching coin data")
                    }
                }
                liveData.postValue(listCoins)
            }
        }

        return liveData
    }
}