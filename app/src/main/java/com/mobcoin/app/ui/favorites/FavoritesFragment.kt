package com.mobcoin.app.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mobcoin.app.R
import com.mobcoin.app.databinding.FragmentFavoritesBinding
import com.mobcoin.app.ui.others.LoadingFragment
import com.mobcoin.app.ui.others.LoggedOutFragment

class FavoritesFragment : Fragment(){

    private var _binding: FragmentFavoritesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val containerId = binding.favoritesFragmentContainer.id


        childFragmentManager.beginTransaction().replace(containerId, LoadingFragment()).commit()

        favoritesViewModel.isConnected(requireContext()).observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                childFragmentManager.beginTransaction().replace(containerId, ConnectedFavoritesFragment()).commit()
            }else{
                childFragmentManager.beginTransaction().replace(containerId, LoggedOutFragment.newInstance(getString(R.string.favorites_not_logged_helper_message))).commit()
            }
        }

        return root
    }

}