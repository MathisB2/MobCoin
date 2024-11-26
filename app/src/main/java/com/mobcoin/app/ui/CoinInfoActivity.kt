package com.mobcoin.app.ui

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import com.mobcoin.app.R
import com.mobcoin.app.databinding.ActivityCoinInfoBinding
import com.mobcoin.app.model.DetailedCoin
import com.mobcoin.app.services.CoinService
import com.mobcoin.app.ui.chart.ChartFragment

import com.squareup.picasso.Picasso

class CoinInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        binding = ActivityCoinInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val toolbar: MaterialToolbar = binding.toolbar

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        val coinInfoViewModel = ViewModelProvider(this)[CoinInfoViewModel::class.java]

        val coinId = intent.getStringExtra("COIN_ID")
        if(coinId == null) {
            finish()
            return
        }

        val chartFragment = ChartFragment.newInstance(coinId, "usd", 1)
        supportFragmentManager.beginTransaction().replace(binding.coinChart.id, chartFragment).commit()


        //DetailedCoin
        coinInfoViewModel.getCoinById(coinId).observe(this){ coin ->
            setPageData(coin ?: return@observe)
            coinInfoViewModel.isFavorite(coin, this).observe(this){
                binding.favoriteCheckbox.isChecked = it
                setupFavoriteClickAction(coin, coinInfoViewModel)
            }
        }

    }


    private fun setupFavoriteClickAction(coin: DetailedCoin, coinInfoViewModel: CoinInfoViewModel){
        binding.favoriteCheckbox.setOnCheckedChangeListener { _, isChecked ->
            //todo : disable checkbox when not logged in
            if (isChecked) {
                coinInfoViewModel.setFavorite(
                    coin = coin,
                    context = this,
                    onSuccess = {
                        Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
                    },
                    onFailure = {
                        Toast.makeText(this, "Error adding to favorites", Toast.LENGTH_SHORT).show()
                    }
                )

            } else {
                coinInfoViewModel.removeFavorite(
                    coin = coin,
                    context = this,
                    onSuccess = {
                        Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show()
                    },
                    onFailure = {
                        Toast.makeText(this, "Error removing from favorites", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }


    private fun setPageData(coin: DetailedCoin){
        supportActionBar?.title = coin.name
        Picasso.get().load(coin.getImageUrlLarge()).into(binding.actionBarCoinIcon)
        CoinService.setPercentageText(coin.marketData?.percentagePriceChange24h,binding.itemCoinEvolution)
        binding.coinPrice.text = "$ " + coin.getPriceByCurrency("usd").toString()
        CoinService.setPercentageText(coin.marketData?.getPercentagePriceChange1hByCurrency("usd"),binding.evolution1h)
        CoinService.setPercentageText(coin.marketData?.percentagePriceChange24h,binding.evolution24h)
        CoinService.setPercentageText(coin.marketData?.percentagePriceChange7d,binding.evolution7d)
        CoinService.setPercentageText(coin.marketData?.percentagePriceChange30d,binding.evolution30d)
        CoinService.setPercentageText(coin.marketData?.percentagePriceChange1y,binding.evolution1y)
        binding.marketCapRankValue.text = coin.marketCapRank.toString()
        binding.totalSupplyValue.text = coin.marketData?.totalSupply.toString()
        binding.marketCapValue.text = coin.marketData?.marketCap?.get("usd").toString()
        binding.athValue.text = coin.marketData?.getAthByCurrency("usd").toString()
        binding.atlValue.text = coin.marketData?.getAtlByCurrency("usd").toString()



        //converter
        binding.textViewCoinConvertName.text = coin.name

        val spinner: Spinner = findViewById(R.id.spinner_converted_coin_name)

        val items = coin.getConvertCoinsNames()?.toList() ?: emptyList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val defaultPosition = items.indexOf("usd")
        if (defaultPosition != -1) {
            spinner.setSelection(defaultPosition)
        }else{
            spinner.setSelection(0)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                binding.textViewConvertedCoinCount.text = coin.getPriceByCurrency(parentView.getItemAtPosition(position).toString()).toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }



    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}