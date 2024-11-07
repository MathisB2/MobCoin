package com.mobcoin.app

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mobcoin.app.databinding.ActivityCoinInfoBinding
import com.mobcoin.app.model.DetailedCoin
import com.mobcoin.app.utils.CoinUtils
import com.squareup.picasso.Picasso

class CoinInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCoinInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val coinInfoViewModel =
            ViewModelProvider(this).get(CoinInfoViewModel::class.java)

        val coinId = intent.getStringExtra("COIN_ID")
        if(coinId == null) {
            finish()
            return
        }

        val chart1 = binding.coinChart
        chart1.entryProducer = coinInfoViewModel.coinChart1EntryModelProducer
        coinInfoViewModel.fetchCoinPrices(coinId,"usd","1")

        //DetailedCoin
        coinInfoViewModel.getCoinById(coinId).observe(this){
            Log.e("TAAAAG", it.toString())
            setPageDatas(it ?: return@observe)
        }

    }

    private fun setPageDatas(coin: DetailedCoin){
        binding.itemCoinName.text = coin.name
        Picasso.get().load(coin.getImageUrlLarge()).into(binding.itemCoinIcon)
        CoinUtils.setPercentageText(coin.marketData?.percentagePriceChange24h,binding.itemCoinEvolution)
        binding.coinPrice.text = coin.getPriceByCurrency("usd").toString()
        CoinUtils.setPercentageText(coin.marketData?.getPercentagePriceChange1hByCurrency("usd"),binding.evolution1h)
        CoinUtils.setPercentageText(coin.marketData?.percentagePriceChange24h,binding.evolution24h)
        CoinUtils.setPercentageText(coin.marketData?.percentagePriceChange7d,binding.evolution7d)
        CoinUtils.setPercentageText(coin.marketData?.percentagePriceChange30d,binding.evolution30d)
        CoinUtils.setPercentageText(coin.marketData?.percentagePriceChange1y,binding.evolution1y)
        binding.marketCapRankValue.text = coin.marketCapRank.toString()
        binding.totalSupplyValue.text = coin.marketData?.totalSupply.toString()
        binding.marketCapValue.text = coin.marketData?.marketCap?.get("usd").toString()
        binding.athValue.text = coin.marketData?.getAthByCurrency("usd").toString()
        binding.atlValue.text = coin.marketData?.getAtlByCurrency("usd").toString()

    }

}