package com.mobcoin.app.ui.me.addCoin

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobcoin.app.domain.AssetRepository
import com.mobcoin.app.domain.GeckoRepository
import com.mobcoin.app.domain.database.model.Asset
import com.mobcoin.app.model.DetailedCoin
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class EditCoinValueViewModel : ViewModel() {

    fun getCoinById(id: String): LiveData<DetailedCoin?> {
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

    fun getCoinQuantity(context: Context, coinId: String): LiveData<Asset?> {
        val asset = MutableLiveData<Asset?>()

        viewModelScope.launch {
            AssetRepository.getAssetWithCoinIdAndUserId(context, coinId).collect {
                asset.postValue(it)
                println(it)
            }
        }

        return asset
    }

    fun editQuantity(context: Context, coinId: String, newQuantity: Double){
        viewModelScope.launch {
            AssetRepository.checkAndUpdateQuantity(context, coinId, newQuantity)
        }
    }

}