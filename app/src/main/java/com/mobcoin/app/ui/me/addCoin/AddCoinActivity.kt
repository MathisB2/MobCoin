package com.mobcoin.app.ui.me.addCoin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobcoin.app.adapter.SearchCoinItemAdapter
import com.mobcoin.app.databinding.ActivityAddCoinBinding
import com.mobcoin.app.services.LanguageService

class AddCoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCoinBinding
    private lateinit var editCoinValueActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        LanguageService.applyLanguage(this)
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


        val searchView = binding.addCoinSearchView
        searchView.editText.addTextChangedListener { text ->
            addCoinViewModel.fetchSearchCoins(text.toString())
        }


        searchView.toolbar.setNavigationOnClickListener {
            finish()
        }

        val searchBar = binding.addCoinSearchBar
        searchBar.post {
            searchBar.performClick()
        }


    }
}