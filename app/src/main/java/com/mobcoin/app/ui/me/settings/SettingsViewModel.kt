package com.mobcoin.app.ui.me.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobcoin.app.domain.UserRepository
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    fun logout(context: Context) {
        viewModelScope.launch {
            UserRepository.logout(context)
        }
    }
}