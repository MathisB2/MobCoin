package com.mobcoin.app.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.mobcoin.app.R
import com.mobcoin.app.adapter.CoinItemAdapter
import com.mobcoin.app.databinding.FragmentHomeBinding
import com.mobcoin.app.databinding.FragmentIsOnlineHomeBinding
import com.mobcoin.app.ui.home.HomeViewModel


class IsOnlineHomeFragment : Fragment() {

    private var _binding: FragmentIsOnlineHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentIsOnlineHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // coins list
        val recyclerView: RecyclerView = binding.recyclerViewCoinList
        val adapter = CoinItemAdapter(homeViewModel.coins.value ?: emptyList(), requireContext()) { coin ->
            val intent = Intent(requireContext(), CoinInfoActivity::class.java).apply {
                putExtra("COIN_ID", coin.id)
            }
            startActivity(intent)
        }

        homeViewModel.coins.observe(viewLifecycleOwner){
            adapter.setDataset(it ?: emptyList())
        }

        recyclerView.adapter = adapter
        homeViewModel.fetchCoins(requireContext())

        return root
    }

}