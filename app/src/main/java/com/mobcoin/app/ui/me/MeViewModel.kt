package com.mobcoin.app.ui.me

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobcoin.app.domain.UserRepository
import kotlinx.coroutines.launch

class MeViewModel : ViewModel() {
    fun isConnected(context: Context): LiveData<Boolean> {
        val isUserConnected = MutableLiveData<Boolean>()
        viewModelScope.launch {
            UserRepository.getCurrentUser(context).collect {
                isUserConnected.postValue(it != null)
            }
        }
        return isUserConnected
    }

    fun logout(context: Context) {
        viewModelScope.launch {
            UserRepository.logout(context)
        }
    }

}