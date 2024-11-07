package com.mobcoin.app.ui.login


import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobcoin.app.domain.database.DBDataSource
import com.mobcoin.app.domain.database.model.User
import com.mobcoin.app.services.ImageService
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    fun createUser(username: String, email: String, password: String, bitmap: Bitmap?){
        val user = User(
            surname = username,
            email =  email,
            password = password,
            profileImage = if (bitmap != null) ImageService.bitmapToByteArray(bitmap) else null
        )

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