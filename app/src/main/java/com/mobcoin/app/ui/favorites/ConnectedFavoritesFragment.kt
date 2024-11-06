package com.mobcoin.app.ui.favorites

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobcoin.app.R

class ConnectedFavoritesFragment : Fragment() {

    companion object {
        fun newInstance() = ConnectedFavoritesFragment()
    }

    private val viewModel: ConnectedFavoritesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_connected_favorites, container, false)
    }
}