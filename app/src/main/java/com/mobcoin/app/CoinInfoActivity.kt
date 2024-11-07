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
    }
}