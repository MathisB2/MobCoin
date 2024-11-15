package com.mobcoin.app.ui.me.addCoin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.mobcoin.app.R
import com.mobcoin.app.databinding.ActivityEditCoinValueBinding
import com.mobcoin.app.model.DetailedCoin
import com.mobcoin.app.services.CoinService

class EditCoinValueActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditCoinValueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityEditCoinValueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val editCoinValueViewModel = ViewModelProvider(this)[EditCoinValueViewModel::class.java]

        val coinId = intent.getStringExtra("COIN_ID")
        if(coinId == null) {
            finish()
            return
        }


        editCoinValueViewModel.getCoinById(coinId).observe(this){
            startTransaction(it ?: return@observe)
        }

    }

    private fun startTransaction(coin: DetailedCoin){
        binding.textViewCoinName.text = coin.symbol
        binding.textViewCoinName.setTextColor(ContextCompat.getColor(this,R.color.md_theme_onSurfaceVariant))

        val priceEditText = binding.countEditText
        val totalTransactionPrice = binding.textViewCoinValue

        priceEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                if (!charSequence.isNullOrEmpty()) {
                    binding.textViewCoinName.setTextColor(ContextCompat.getColor(baseContext,R.color.md_theme_onBackground))
                    val price = charSequence.toString().toDoubleOrNull()
                    if (price != null) {
                        totalTransactionPrice.text = CoinService.roundDoubleToTwoDecimals(price * coin.getPriceByCurrency("usd")!!)
                    }
                }else{
                    binding.textViewCoinName.setTextColor(ContextCompat.getColor(baseContext,R.color.md_theme_onSurfaceVariant))
                }
            }

            override fun afterTextChanged(editable: Editable?) {}
        })
    }
}