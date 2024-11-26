package com.mobcoin.app.ui.me.addCoin

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
import com.mobcoin.app.domain.database.model.Asset
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


        editCoinValueViewModel.getCoinById(coinId).observe(this){
            startTransaction(it ?: return@observe)
        }

        editCoinValueViewModel.getCoinQuantity(this, coinId).observe(this){
            binding.textViewAccountQuantityValue.text = it?.quantity?.toString() ?: "0"
        }

        binding.buttonBuy.setOnClickListener {


            Toast.makeText(this,"buy successful",Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.buttonSell.setOnClickListener {


            Toast.makeText(this,"sell successful",Toast.LENGTH_SHORT).show()
            finish()
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
                totalTransactionPrice.text = CoinService.roundDoubleToTwoDecimals(
                    (charSequence?.toString()?.toDoubleOrNull() ?: 0.0) * (coin.getPriceByCurrency("usd") ?: 1.0)
                )

                if (!charSequence.isNullOrEmpty() && charSequence.toString().toDouble() == 0.0) {
                    binding.textViewCoinName.setTextColor(ContextCompat.getColor(baseContext,R.color.md_theme_onBackground))

                    binding.buttonBuy.isEnabled = true
                    if(charSequence.toString().toDouble() <= coinQuantity){
                        binding.buttonSell.isEnabled = true
                    }
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

    private fun setAccountQuantity(asset: Asset){

    }
}