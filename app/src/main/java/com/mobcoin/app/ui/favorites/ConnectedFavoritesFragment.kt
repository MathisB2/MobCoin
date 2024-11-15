package com.mobcoin.app.ui.favorites

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mobcoin.app.R
import com.mobcoin.app.adapter.CoinItemAdapter
import com.mobcoin.app.adapter.FavoriteCoinItemAdapter
import com.mobcoin.app.databinding.FragmentConnectedFavoritesBinding
import com.mobcoin.app.databinding.FragmentFavoritesBinding
import com.mobcoin.app.model.Coin
import com.mobcoin.app.services.ConnectivityService
import com.mobcoin.app.ui.CoinInfoActivity
import com.mobcoin.app.ui.others.LoggedOutFragment

class ConnectedFavoritesFragment : Fragment() {
    private var _binding: FragmentConnectedFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val favoritesViewModel = ViewModelProvider(this).get(ConnectedFavoritesViewModel::class.java)

        _binding = FragmentConnectedFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerViewCoinsFavorites

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = gridLayoutManager

        val coinTest = Coin("bitcoin", "","10", "bitcoin", 10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0, emptyList())
        val adapter = FavoriteCoinItemAdapter(emptyList(), childFragmentManager, onFavoriteItemClick())

        val coinsTestList : ArrayList<Coin> = ArrayList()
        coinsTestList.add(coinTest)
        coinsTestList.add(coinTest)
        coinsTestList.add(coinTest)
        adapter.setDataset(coinsTestList)

        recyclerView.adapter = adapter

        return root
    }

    private fun onFavoriteItemClick(): (Coin) -> Unit = { coin ->
        val intent = Intent(requireContext(), CoinInfoActivity::class.java).apply {
            putExtra("COIN_ID", coin.id)
        }

        startActivity(intent)
    }


}