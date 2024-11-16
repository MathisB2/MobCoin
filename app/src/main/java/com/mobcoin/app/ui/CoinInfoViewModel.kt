package com.mobcoin.app.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobcoin.app.domain.FavoriteRepository
import com.mobcoin.app.domain.GeckoRepository
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

    fun isFavorite(coin: DetailedCoin, context: Context) : LiveData<Boolean>{
        val isFavorite = MutableLiveData<Boolean>()

        viewModelScope.launch {
            FavoriteRepository.isFavorite(coin, context).catch {
                isFavorite.postValue(false)
            }.collect {
                isFavorite.postValue(it)
            }


        }
        return isFavorite
    }


    fun setFavorite(coin : DetailedCoin, context: Context, onSuccess: () -> Unit, onFailure: () -> Unit){
        viewModelScope.launch {
            try{
                FavoriteRepository.setFavorite(coin, context)
                onSuccess()
            }catch (e: Exception){
                onFailure()
            }
        }
    }

    fun removeFavorite (coin: DetailedCoin, context: Context, onSuccess: () -> Unit, onFailure: () -> Unit){
        viewModelScope.launch {
            try{
                FavoriteRepository.removeFavorite(coin, context)
                onSuccess()
            }catch (e: Exception){
                onFailure()
            }
        }
    }

}