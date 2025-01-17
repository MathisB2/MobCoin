package com.mobcoin.app.ui.favorites

import android.content.Context
import android.media.Image
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobcoin.app.domain.FavoriteRepository
import com.mobcoin.app.domain.GeckoRepository
import com.mobcoin.app.model.DetailedCoin
import com.mobcoin.app.model.FavoriteCoin
import com.mobcoin.app.services.ImageService
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ConnectedFavoritesViewModel : ViewModel() {

    fun getFavoriteCoins(context: Context): LiveData<List<FavoriteCoin>> {
        val liveData = MutableLiveData<List<FavoriteCoin>>()

        viewModelScope.launch {
            FavoriteRepository.getFavorites(context).collect {
                val listCoins = ArrayList<FavoriteCoin>()

                it.forEach { coinData ->
                    //create a favoriteCoin with default values
                    val favoriteCoin = FavoriteCoin(
                        coinData.geckoId,
                        coinData.code,
                        coinData.name,
                        0.0,
                        null,
                        ImageService.byteArrayToBitmap(coinData.icon),
                        null
                    )


                    //try to fetch more data online
                    val response = GeckoRepository.getCoinById(coinData.geckoId)
                    response.catch { error ->
                        Log.e("ConnectedFavoritesViewModel", error.message.toString())
                    }.collect{ response ->
                        if (response.isSuccessful)
                            response.body()?.let { detailedCoin ->
                                favoriteCoin.percentagePriceChange = detailedCoin.marketData?.percentagePriceChange24h ?: 0.0
                                favoriteCoin.marketData = detailedCoin.marketData
                                favoriteCoin.imageUrl = detailedCoin.getImageUrlSmall()
                            }
                        else
                            Log.e("ConnectedFavoritesViewModel", "Error fetching coin data")
                    }

                    listCoins.add(favoriteCoin)
                }
                liveData.postValue(listCoins)
            }
        }

        return liveData
    }
}