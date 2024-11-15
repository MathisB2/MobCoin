package com.mobcoin.app.ui.login


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobcoin.app.domain.UserRepository
import com.mobcoin.app.services.UserService
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {


    fun login(email: String, password: String, context: Context,  onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch {
            val user = UserRepository.getUserByEmail(email).firstOrNull()
            if (user == null) {
                onFailure()
                return@launch
            }

            if (user.password != UserService.hashPassword(password)) {
                onFailure()
                return@launch
            }

            UserRepository.login(user, context)
            onSuccess()
        }
    }
}