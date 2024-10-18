package com.mobcoin.app

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.mobcoin.app.databinding.ActivityCoinInfoBinding
import com.mobcoin.app.databinding.ActivityMainBinding
import com.mobcoin.app.databinding.FragmentMeBinding
import com.mobcoin.app.ui.home.HomeViewModel
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryOf
import kotlin.random.Random

class CoinInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_coin_info)

        binding = ActivityCoinInfoBinding.inflate(layoutInflater)

        val coinInfoViewModel =
            ViewModelProvider(this).get(CoinInfoViewModel::class.java)

        val coinId = intent.getStringExtra("COIN_ID")
        if(coinId == null) {
            finish()
            return
        }

        val coinChart =  binding.coinChart
        coinChart.entryProducer = ChartEntryModelProducer(List(4) { entryOf(it, Random.nextFloat() * 16f) })
        coinChart.setModel(ChartEntryModelProducer(List(4) { entryOf(it, Random.nextFloat() * 16f) }).getModel())
        //coinInfoViewModel.fetchCoinPrices(coinId,"usd","1")

    }
}