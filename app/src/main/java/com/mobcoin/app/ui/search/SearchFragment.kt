package com.mobcoin.app.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobcoin.app.adapter.SearchCoinItemAdapter
import com.mobcoin.app.databinding.FragmentSearchBinding
import com.mobcoin.app.ui.CoinInfoActivity


class SearchFragment : Fragment(){

    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val searchView = binding.searchView

        // search coins list
        val recyclerView = binding.recyclerViewCoinSearchList
        val llm = LinearLayoutManager(requireContext())
        val adapter = SearchCoinItemAdapter(requireContext(), searchViewModel.searchCoins.value ?: emptyList()) { searchCoin ->
            val intent = Intent(requireContext(), CoinInfoActivity::class.java).apply {
                putExtra("COIN_ID", searchCoin.id)
            }
            startActivity(intent)
        }

        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setLayoutManager(llm)
        recyclerView.setAdapter(adapter)


        searchViewModel.searchCoins.observe(viewLifecycleOwner){
            adapter.setDataset(it ?: emptyList())
        }

        recyclerView.adapter = adapter

        searchView.editText.addTextChangedListener { text ->
            searchViewModel.fetchSearchCoins(text.toString())
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}