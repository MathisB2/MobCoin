package com.mobcoin.app.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mobcoin.app.R
import com.mobcoin.app.adapter.FavoriteCoinItemAdapter
import com.mobcoin.app.databinding.FragmentConnectedFavoritesBinding
import com.mobcoin.app.model.FavoriteCoin
import com.mobcoin.app.ui.CoinInfoActivity

class ConnectedFavoritesFragment : Fragment() {
    private var _binding: FragmentConnectedFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewFavoriteActivityLauncher: ActivityResultLauncher<Intent>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val favoritesViewModel = ViewModelProvider(this)[ConnectedFavoritesViewModel::class.java]

        viewFavoriteActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            favoritesViewModel.fetchFavoriteCoins(requireContext())
        }

        _binding = FragmentConnectedFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerViewCoinsFavorites

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = gridLayoutManager

        val adapter = FavoriteCoinItemAdapter(emptyList(), childFragmentManager, onFavoriteItemClick(), requireContext())

        favoritesViewModel.favorites.observe(viewLifecycleOwner){
            if(it!!.isEmpty()){
                binding.linearLayoutEmptyFavorites.visibility = View.VISIBLE
            } else {
                binding.linearLayoutEmptyFavorites.visibility = View.GONE
            }
            adapter.setDataset(it)
        }

        favoritesViewModel.fetchFavoriteCoins(requireContext())

        val firstFavoriteButton = binding.favoritesFragmentFirstFavoritesButton
        firstFavoriteButton.setOnClickListener {


            requireActivity().findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_home)
//            parentFragmentManager.p.beginTransaction().replace(R.id.nav_host_fragment_activity_main, HomeFragment()).commit()
        }

        recyclerView.adapter = adapter

        return root
    }

    private fun onFavoriteItemClick(): (FavoriteCoin) -> Unit = { coin ->

        val intent = Intent(requireContext(), CoinInfoActivity::class.java).apply {
            putExtra("COIN_ID", coin.id)
        }


        viewFavoriteActivityLauncher.launch(intent)
    }


}