package com.mobcoin.app.ui.me.settings

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mobcoin.app.BuildConfig
import com.mobcoin.app.MainActivity
import com.mobcoin.app.R
import com.mobcoin.app.databinding.ActivitySettingsBinding
import com.mobcoin.app.model.Language
import com.mobcoin.app.services.ConnectivityService
import com.mobcoin.app.services.CurrencyService
import com.mobcoin.app.services.ImageService
import com.mobcoin.app.services.LanguageService

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        LanguageService.applyLanguage(this)
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val topAppBar = binding.toolbar
        setSupportActionBar(topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        val currencyInput: AutoCompleteTextView = binding.autoCompleteTextViewCurrency
        val languageInput: AutoCompleteTextView = binding.autoCompleteTextViewLanguages
        languageInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {


            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.appVersion.text = "Version: ${BuildConfig.BUILD_TYPE} ${BuildConfig.VERSION_NAME}"

        binding.buttonApply.setOnClickListener {

            CurrencyService.setCurrency(this@SettingsActivity, currencyInput.text.toString())
            LanguageService.setLanguage(this@SettingsActivity, Language.getLanguageCode(languageInput.text.toString()))
            restartApp()
        }

        binding.SettingsButtonLogout.setOnClickListener {
            settingsViewModel.logout(this)
            restartApp()
        }

        if(ConnectivityService.isOnline(this)){
            settingsViewModel.getUser(this).observe(this) {
                if(it != null){
                    binding.settingsUsername.text = it.surname
                    binding.settingsEmail.text = it.email

                    if(it.profileImage != null){
                        val userBitmap = ImageService.byteArrayToBitmap(it.profileImage)
                        binding.settingsProfilePicture.setImageBitmap(userBitmap)
                    }
                    setSettingUserDataVisibility(View.VISIBLE)
                }else{
                    setSettingUserDataVisibility(View.GONE)
                }
            }
        }else{
            setSettingUserDataVisibility(View.GONE)
        }
    }

    fun setSettingUserDataVisibility(visibility: Int){
        binding.settingsUsername.visibility = visibility
        binding.settingsEmail.visibility = visibility
        binding.settingsProfilePicture.visibility = visibility
        binding.SettingsButtonLogout.visibility = visibility
    }

    fun restartApp(){
        val intent = Intent(this@SettingsActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        finish()
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        val language = resources.getStringArray(R.array.languages)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, language)
        binding.autoCompleteTextViewLanguages.setAdapter(arrayAdapter)

        binding.autoCompleteTextViewCurrency.text = Editable.Factory.getInstance().newEditable(CurrencyService.getCurrency(this))
        val currency = resources.getStringArray(R.array.currency)
        val currencyAdapter = ArrayAdapter(this, R.layout.dropdown_item, currency)
        binding.autoCompleteTextViewCurrency.setAdapter(currencyAdapter)

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}