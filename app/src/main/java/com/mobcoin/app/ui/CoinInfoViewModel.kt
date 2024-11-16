package com.mobcoin.app.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobcoin.app.domain.FavoriteRepository
import com.mobcoin.app.domain.GeckoRepository
import com.mobcoin.app.model.Coin
import com.mobcoin.app.model.DetailedCoin
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


class CoinInfoViewModel : ViewModel() {
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


    fun setFavorite(coin : DetailedCoin, context: Context){
        viewModelScope.launch {
            FavoriteRepository.setFavorite(coin, context)
        }
    }

}