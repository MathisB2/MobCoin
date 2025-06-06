package com.mobcoin.app.ui.favorites

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobcoin.app.domain.UserRepository
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {

    fun isConnected(context: Context): LiveData<Boolean> {
        val isUserConnected = MutableLiveData<Boolean>()
        viewModelScope.launch {
            UserRepository.getCurrentUser(context).collect {
                isUserConnected.postValue(it != null)
            }
        }
        return isUserConnected
    }
}