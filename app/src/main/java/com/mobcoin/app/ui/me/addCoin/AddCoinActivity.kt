package com.mobcoin.app.ui.me.addCoin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobcoin.app.adapter.SearchCoinItemAdapter
import com.mobcoin.app.databinding.ActivityAddCoinBinding
import com.mobcoin.app.ui.me.MeFragment
import com.mobcoin.app.ui.me.MeViewModel

class AddCoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCoinBinding
    private lateinit var editCoinValueActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddCoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editCoinValueActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK){
                val isSuccessful = result.data?.getBooleanExtra("isSuccessful", false) ?: false
                if (isSuccessful){
                    finish()
                }
            }
        }

        val addCoinViewModel = ViewModelProvider(this)[AddCoinViewModel::class.java]

        val searchEditText = binding.searchEditText
        val recyclerView = binding.recyclerViewAddCoin
        val llm = LinearLayoutManager(this)

        val adapter = SearchCoinItemAdapter( addCoinViewModel.searchCoins.value ?: emptyList()) { searchCoin ->
            val intent = Intent(this, EditCoinValueActivity::class.java).apply {
                putExtra("COIN_ID", searchCoin.id)
            }
            editCoinValueActivityLauncher.launch(intent)
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