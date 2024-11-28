package com.mobcoin.app.ui.me

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.viewModelScope
import com.mobcoin.app.domain.AssetRepository
import com.mobcoin.app.domain.GeckoRepository
import com.mobcoin.app.domain.UserRepository
import com.mobcoin.app.domain.database.model.Asset
import com.mobcoin.app.domain.database.model.User
import com.mobcoin.app.domain.httpQuery.MarketCoinsQuery
import com.mobcoin.app.model.Coin
import com.mobcoin.app.model.DetailedCoin
import com.mobcoin.app.model.DisplayedAsset
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

class MeViewModel : ViewModel() {

    private var _displayedAssets : MutableLiveData<List<DisplayedAsset>?> = MutableLiveData()
    val displayedAssets : LiveData<List<DisplayedAsset>?> = _displayedAssets

    private var _accountValue : MutableLiveData<Double> = MutableLiveData()
    val accountValue : LiveData<Double> = _accountValue

    fun isConnected(context: Context): LiveData<Boolean> {
        val isUserConnected = MutableLiveData<Boolean>()
        viewModelScope.launch {
            UserRepository.getCurrentUser(context).collect {
                isUserConnected.postValue(it != null)
            }
        }
        return isUserConnected
    }


    fun getUser(context: Context): LiveData<User> {
        val username = MutableLiveData<User>()
        viewModelScope.launch {
            UserRepository.getCurrentUser(context).collect {
                username.postValue(it)
            }
        }
        return username
    }

    fun logout(context: Context) {
        viewModelScope.launch {
            UserRepository.logout(context)
        }
    }

    fun fetchDisplayedAssets(context: Context) {
        val dAssets: MutableList<DisplayedAsset> = mutableListOf()
        var totalAccountValue: Double = 0.0

        viewModelScope.launch {
            AssetRepository.getAllAssetWithUserId(context).collect { assets ->

                assets.forEach{ asset ->
                    if(asset.quantity > 0) {
                        val data = GeckoRepository.getCoinById(asset.coinId)
                        data.catch { e ->
                            return@catch
                        }.collect { response ->
                            dAssets.add(DisplayedAsset(
                                coinId = asset.coinId,
                                quantity = asset.quantity,
                                coinName = response.body()?.name ?: "",
                                coinSymbol = response.body()?.symbol ?: "",
                                coinPrice = response.body()?.getPriceByCurrency("usd") ?: 0.0,
                                coinChange = response.body()?.marketData?.percentagePriceChange24h ?: 0.0,
                                coinIcon = response.body()?.getImageUrlSmall() ?: ""
                            )
                            )
                            totalAccountValue += asset.quantity * response.body()?.getPriceByCurrency("usd")!!
                        }
                    }
                }
                dAssets.sortByDescending { it.quantity * it.coinPrice }
                _displayedAssets.postValue(dAssets)
                _accountValue.postValue(totalAccountValue)
            }
        }
    }

    fun getTotalAccountValue(){

    }

}