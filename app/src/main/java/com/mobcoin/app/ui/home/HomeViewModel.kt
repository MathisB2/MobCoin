package com.mobcoin.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobcoin.app.domain.FNGRepository
import com.mobcoin.app.model.FNG
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel : ViewModel() {

    private var _fng : MutableLiveData<FNG?> = MutableLiveData()

    val fng : LiveData<FNG?> = _fng

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

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

}