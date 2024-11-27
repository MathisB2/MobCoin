package com.mobcoin.app.ui.me.addCoin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
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

    private var coinQuantity: Double = 0.0

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
        println("coinidddddddd" + coinId)

        editCoinValueViewModel.getCoinById(coinId).observe(this){
            if(it == null) return@observe
            editCoinValueViewModel.getCoinQuantity(this, coinId).observe(this){ asset ->
                binding.textViewAccountQuantityValue.text = asset?.quantity?.toString() ?: "0"
                coinQuantity = asset?.quantity ?: 0.0
                startTransaction(it)
            }


        }

        editCoinValueViewModel.getCoinQuantity(this, coinId).observe(this){
            binding.textViewAccountQuantityValue.text = it?.quantity?.toString() ?: "0"
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
                    (charSequence?.toString()?.toDoubleOrNull() ?: 0.0) * (coin.getPriceByCurrency("usd") ?: 1.0)
                ) + "$"

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
}