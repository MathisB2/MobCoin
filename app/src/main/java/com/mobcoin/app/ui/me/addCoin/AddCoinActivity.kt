package com.mobcoin.app.ui.me.addCoin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.mobcoin.app.R
import com.mobcoin.app.databinding.ActivityAddCoinBinding
import com.mobcoin.app.databinding.ActivityCoinInfoBinding

class AddCoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddCoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addCoinViewModel = ViewModelProvider(this)[AddCoinViewModel::class.java]





    }
}