package com.mobcoin.app.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {


    fun login(email:String, password:String): LiveData<Boolean>{
        val liveData = MutableLiveData<Boolean>()


        /* todo
        viewModelScope.launch {
            val data = UserRepository.getUserByLogin(email, password)
            data.catch { e ->
                Log.d("error",e)
            }.collect { response ->
                if (response.isSuccessful) {
                    liveData.postValue(true)
                } else {
                    liveData.postValue(false)
                }
            }

        }*/

        liveData.postValue(true)

        return liveData
    }
}