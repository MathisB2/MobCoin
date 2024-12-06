package com.mobcoin.app.ui.me.addCoin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.mobcoin.app.R
import com.mobcoin.app.databinding.ActivityEditCoinValueBinding
import com.mobcoin.app.model.Currency
import com.mobcoin.app.model.DetailedCoin
import com.mobcoin.app.services.CoinService
import com.mobcoin.app.services.CurrencyService
import com.mobcoin.app.services.LanguageService
import com.squareup.picasso.Picasso

class EditCoinValueActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditCoinValueBinding

    private var coinQuantity: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        LanguageService.applyLanguage(this)
        super.onCreate(savedInstanceState)

        binding = ActivityEditCoinValueBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val topAppBar = binding.toolbar
        setSupportActionBar(topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val editCoinValueViewModel = ViewModelProvider(this)[EditCoinValueViewModel::class.java]

        val coinId = intent.getStringExtra("COIN_ID")
        if(coinId == null) {
            finish()
            return
        }

        editCoinValueViewModel.getCoinById(coinId).observe(this){
            if(it == null) return@observe
            Picasso.get().load(it.getImageUrlLarge()).into(binding.actionBarCoinIcon)
            supportActionBar?.title = it.name
            editCoinValueViewModel.getCoinQuantity(this, coinId).observe(this){ asset ->
                binding.textViewAccountQuantityValue.text = asset?.quantity?.toString() ?: "0"
                coinQuantity = asset?.quantity ?: 0.0
                startTransaction(it)
            }


        }

        editCoinValueViewModel.getCoinQuantity(this, coinId).observe(this){
            binding.textViewAccountQuantityValue.text = it?.quantity?.toString() ?: "0"
            showKeyboard(binding.countEditText)
        }


        binding.buttonBuy.setOnClickListener {
            editCoinValueViewModel.editQuantity(this, coinId, binding.countEditText.text.toString().toDouble())

            Toast.makeText(this,"buy successful",Toast.LENGTH_SHORT).show()
            val resultIntent = Intent()
            resultIntent.putExtra("isSuccessful", true)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        binding.buttonSell.setOnClickListener {
            editCoinValueViewModel.editQuantity(this, coinId, binding.countEditText.text.toString().toDouble() * -1)

            Toast.makeText(this,"sell successful",Toast.LENGTH_SHORT).show()
            val resultIntent = Intent()
            resultIntent.putExtra("isSuccessful", true)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }


    }

    private fun startTransaction(coin: DetailedCoin){
        binding.textViewCoinName.text = coin.symbol.uppercase()
        binding.textViewCoinName.setTextColor(ContextCompat.getColor(this,R.color.md_theme_onSurfaceVariant))

        val priceEditText = binding.countEditText
        val totalTransactionPrice = binding.textViewCoinValue

        priceEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                totalTransactionPrice.text = CoinService.roundDoubleToTwoDecimals(
                    (charSequence?.toString()?.toDoubleOrNull() ?: 0.0) * (coin.getPriceByCurrency(
                        CurrencyService.getCurrency(this@EditCoinValueActivity)) ?: 1.0)
                ) + Currency.getCurrencySymbole(CurrencyService.getCurrency(this@EditCoinValueActivity))

                if (!charSequence.isNullOrEmpty() && charSequence.toString().toDouble() != 0.0) {
                    binding.textViewCoinName.setTextColor(ContextCompat.getColor(baseContext,R.color.md_theme_onBackground))

                    binding.buttonBuy.isEnabled = true
                    println(charSequence.toString().toDouble())
                    println(coinQuantity)
                    binding.buttonSell.isEnabled = charSequence.toString().toDouble() <= coinQuantity

                }else{
                    binding.textViewCoinName.setTextColor(ContextCompat.getColor(baseContext,R.color.md_theme_onSurfaceVariant))


                    binding.buttonBuy.isEnabled = false
                    binding.buttonSell.isEnabled = false

                }
            }

            override fun afterTextChanged(editable: Editable?) {}
        })

        binding.textViewCoinName2.text = coin.symbol

    }


    private fun showKeyboard(focusableEditText: EditText) {
        focusableEditText.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(focusableEditText, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}