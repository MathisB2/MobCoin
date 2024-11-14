package com.mobcoin.app.ui.login


import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobcoin.app.domain.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    fun createUser(username: String, email: String, password: String, bitmap: Bitmap?, context: Context){
        viewModelScope.launch {
            UserRepository.createUser(username, email, password, bitmap).collect{
                UserRepository.login(it, context).collect{
                    Log.d("RegisterViewModel", it.toString())
                    UserRepository.getCurrentUser(context).collect{
                        Log.d("RegisterViewModel", it!!.email)
                    }
                }




            }
        }
    }

    fun checkUserExists(email: String): LiveData<Boolean> {
        val isUserExisting = MutableLiveData<Boolean>()

        viewModelScope.launch {
            UserRepository.checkUserExists(email).collect {
                isUserExisting.postValue(it)
            }
        }
        return isUserExisting
    }

}