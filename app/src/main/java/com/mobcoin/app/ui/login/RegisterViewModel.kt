package com.mobcoin.app.ui.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobcoin.app.domain.database.DBDataSource
import com.mobcoin.app.domain.database.model.User
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    fun createUser(username: String, email: String, password: String){
        val user = User( surname = username, email =  email, password = password)

        viewModelScope.launch {
            DBDataSource.getDatabase().userDao().insert(user)
        }
    }

    fun checkUserExists(email: String): LiveData<Boolean> {
        val isUserExisting = MutableLiveData<Boolean>()

        viewModelScope.launch {
            isUserExisting.postValue(DBDataSource.getDatabase().userDao().isUserExisting(email))
        }

        return isUserExisting
    }

}