package com.mobcoin.app.ui.favorites

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mobcoin.app.adapter.FavoriteCoinItemAdapter
import com.mobcoin.app.databinding.FragmentConnectedFavoritesBinding
import com.mobcoin.app.model.DetailedCoin
import com.mobcoin.app.ui.CoinInfoActivity

class ConnectedFavoritesFragment : Fragment() {
    private var _binding: FragmentConnectedFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val favoritesViewModel = ViewModelProvider(this)[ConnectedFavoritesViewModel::class.java]

        _binding = FragmentConnectedFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerViewCoinsFavorites

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = gridLayoutManager

        val adapter = FavoriteCoinItemAdapter(emptyList(), childFragmentManager, onFavoriteItemClick())

        favoritesViewModel.getFavoriteCoins(requireContext()).observe(viewLifecycleOwner){
            adapter.setDataset(it)
        }

        recyclerView.adapter = adapter

        return root
    }

    private fun onFavoriteItemClick(): (DetailedCoin) -> Unit = { coin ->
        val intent = Intent(requireContext(), CoinInfoActivity::class.java).apply {
            putExtra("COIN_ID", coin.id)
        }

        startActivity(intent)
    }


}