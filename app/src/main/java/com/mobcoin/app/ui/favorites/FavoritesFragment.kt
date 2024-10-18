package com.mobcoin.app.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mobcoin.app.databinding.FragmentFavoritesBinding
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




        var logged: Boolean = false

        if(logged){
//            childFragmentManager.beginTransaction().replace(fragmentId, LoggedOutFragment()).commit() //todo: 
        }else{
            childFragmentManager.beginTransaction().replace(binding.favoritesFragmentContainer.id, LoggedOutFragment()).commit()
        }



        return root
    }

}