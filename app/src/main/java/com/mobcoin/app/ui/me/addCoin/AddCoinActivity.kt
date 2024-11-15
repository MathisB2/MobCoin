package com.mobcoin.app.ui.me.addCoin

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobcoin.app.R
import com.mobcoin.app.adapter.SearchCoinItemAdapter
import com.mobcoin.app.databinding.ActivityAddCoinBinding
import com.mobcoin.app.databinding.ActivityCoinInfoBinding
import com.mobcoin.app.ui.CoinInfoActivity

class AddCoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddCoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addCoinViewModel = ViewModelProvider(this)[AddCoinViewModel::class.java]

        val searchEditText = binding.searchEditText
        val recyclerView = binding.recyclerViewAddCoin
        val llm = LinearLayoutManager(this)

        val adapter = SearchCoinItemAdapter(this, addCoinViewModel.searchCoins.value ?: emptyList()) { searchCoin ->
            val intent = Intent(this, EditCoinValueActivity::class.java).apply {
                putExtra("COIN_ID", searchCoin.id)
            }
            startActivity(intent)
        }

        recyclerView.setLayoutManager(llm)
        recyclerView.setAdapter(adapter)

        addCoinViewModel.searchCoins.observe(this){
            adapter.setDataset(it ?: emptyList())
        }

        recyclerView.adapter = adapter

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                addCoinViewModel.fetchSearchCoins(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable?) {}
        })

    }
}