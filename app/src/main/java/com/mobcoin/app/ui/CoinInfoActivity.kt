package com.mobcoin.app.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.mobcoin.app.R
import com.mobcoin.app.adapter.ExchangeItemAdapter
import com.mobcoin.app.databinding.ActivityCoinInfoBinding
import com.mobcoin.app.model.Currency
import com.mobcoin.app.model.DetailedCoin
import com.mobcoin.app.model.Exchange
import com.mobcoin.app.services.CoinService
import com.mobcoin.app.services.CurrencyService
import com.mobcoin.app.services.LanguageService
import com.mobcoin.app.ui.chart.ChartFragment
import com.squareup.picasso.Picasso

class CoinInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        LanguageService.applyLanguage(this)
        super.onCreate(savedInstanceState)

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

        val chartFragment = ChartFragment.newInstance(coinId, CurrencyService.getCurrency(this), 1)
        supportFragmentManager.beginTransaction().replace(binding.coinChart.id, chartFragment).commit()

        binding.chartRangeSelector.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            if (isChecked) {
                val days = when (checkedId) {
                    binding.button24h.id -> 1
                    binding.button7d.id -> 7
                    binding.button30d.id -> 30
                    binding.button90d.id -> 90
                    binding.button1y.id -> 365
                    else -> 1
                }
                chartFragment.setDays(days)
            }
        }

        binding.converterAddButton.setOnClickListener { increaseCoinCount(1.0) }
        binding.converterRemoveButton.setOnClickListener { increaseCoinCount(-1.0) }



        //DetailedCoin
        coinInfoViewModel.getCoinById(coinId).observe(this){ coin ->
            setPageData(coin ?: return@observe)
            coinInfoViewModel.isFavorite(coin, this).observe(this){
                binding.favoriteCheckbox.isChecked = it
                setupFavoriteClickAction(coin, coinInfoViewModel)
                setupConvertCoinEditAction(coin)
                setExchangesList(coin, coinInfoViewModel)
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

    private fun setupConvertCoinEditAction(coin: DetailedCoin){
        binding.convertInputEditTextValue.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                updateConverterResult(coin)
            }

            override fun afterTextChanged(editable: Editable?) {}
        })
    }


    private fun setPageData(coin: DetailedCoin){
        supportActionBar?.title = coin.name
        Picasso.get().load(coin.getImageUrlLarge()).into(binding.actionBarCoinIcon)
        CoinService.setPercentageText(coin.marketData?.percentagePriceChange24h,binding.itemCoinEvolution)
        binding.coinPrice.text = coin.getPriceByCurrency(CurrencyService.getCurrency(this)).toString() + " " + Currency.getCurrencySymbole(CurrencyService.getCurrency(this))
        CoinService.setPercentageText(coin.marketData?.getPercentagePriceChange1hByCurrency(CurrencyService.getCurrency(this)),binding.evolution1h)
        CoinService.setPercentageText(coin.marketData?.percentagePriceChange24h,binding.evolution24h)
        CoinService.setPercentageText(coin.marketData?.percentagePriceChange7d,binding.evolution7d)
        CoinService.setPercentageText(coin.marketData?.percentagePriceChange30d,binding.evolution30d)
        CoinService.setPercentageText(coin.marketData?.percentagePriceChange1y,binding.evolution1y)
        binding.marketCapRankValue.text = "#"+coin.marketCapRank.toString()
        binding.totalSupplyValue.text = CoinService.formatNumber(coin.marketData?.totalSupply)
        binding.marketCapValue.text = CoinService.formatNumber(coin.marketData?.marketCap?.get(CurrencyService.getCurrency(this))) + Currency.getCurrencySymbole(CurrencyService.getCurrency(this))
        binding.athValue.text = coin.marketData?.getAthByCurrency(CurrencyService.getCurrency(this)).toString() + Currency.getCurrencySymbole(CurrencyService.getCurrency(this))
        binding.atlValue.text = coin.marketData?.getAtlByCurrency(CurrencyService.getCurrency(this)).toString() + Currency.getCurrencySymbole(CurrencyService.getCurrency(this))

//        binding.volume24hValue.text = coin.communityData.twitterFollowers.toString();

        try {
            if(coin.communityData.twitterFollowers < 10_000)
                binding.trustBar.setImageResource(R.drawable.trust1)
            else if(coin.communityData.twitterFollowers < 50_000)
                binding.trustBar.setImageResource(R.drawable.trust2)
            else if(coin.communityData.twitterFollowers < 100_000)
                binding.trustBar.setImageResource(R.drawable.trust3)
            else
                binding.trustBar.setImageResource(R.drawable.trust4)
        }catch (e: Exception){
            binding.trustBar.setImageResource(R.drawable.trust1)
        }



        //converter
        binding.textViewCoinConvertName.text = coin.symbol.uppercase()

        val spinner = binding.spinnerConvertedCoinName



        val items = coin.getConvertCoinsNames()?.toList() ?: emptyList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val defaultPosition = items.indexOf(CurrencyService.getCurrency(this))
        if (defaultPosition != -1) {
            spinner.setSelection(defaultPosition)
        }else{
            spinner.setSelection(0)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                updateConverterResult(coin)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }



    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun increaseCoinCount(quantity: Double) {
        val currentValue = binding.convertInputEditTextValue.text.toString().toDoubleOrNull() ?: 1.0
        binding.convertInputEditTextValue.setText((currentValue + quantity).toString())
    }

    private fun updateConverterResult(coin: DetailedCoin) {
        val valueToConvert = binding.convertInputEditTextValue.text.toString().toDoubleOrNull() ?: 1.0
        val selectedCurrency = binding.spinnerConvertedCoinName.selectedItem.toString()
        val result = coin.getPriceByCurrency(selectedCurrency)!! * valueToConvert
        binding.textViewConvertedCoinCount.text = result.toString()
    }

    private fun setExchangesList(coin: DetailedCoin, viewModel: CoinInfoViewModel){
        val coinTicker = coin.tickers?.take(10)
        val exchangeRecyclerView: RecyclerView = binding.recyclerViewExchange
        val adapter = ExchangeItemAdapter(coinTicker!!, this)
        exchangeRecyclerView.layoutManager = LinearLayoutManager(this)
        exchangeRecyclerView.adapter = adapter

        val exchanges = mutableListOf<Exchange>()
        for (ticker in coinTicker){
            val foundedExchange  = exchanges.find { it.name == ticker.exchange.name }
            if(foundedExchange == null){
                viewModel.getExchangeById(ticker.exchange.identifier).observe(this){
                    if (it != null) {
                        exchanges.add(it)
                        ticker.exchange.image = it.image
                        adapter.notifyDataSetChanged()
                    }

                }
            }else{
                ticker.exchange.image = foundedExchange.image
                adapter.notifyDataSetChanged()
            }


        }




    }

}