package com.mobcoin.app.ui.me

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.mobcoin.app.databinding.FragmentMeBinding
import com.mobcoin.app.services.ConnectivityService
import com.mobcoin.app.ui.internet.OfflineFragment
import com.mobcoin.app.ui.me.settings.SettingsActivity
import com.mobcoin.app.ui.others.LoadingFragment
import com.mobcoin.app.ui.others.LoggedOutFragment

class MeFragment : Fragment(){

    private var _binding: FragmentMeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val meViewModel = ViewModelProvider(this).get(MeViewModel::class.java)


        val settingsButton: ImageButton = binding.imageButton
        settingsButton.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(intent)
        }


        childFragmentManager.beginTransaction().replace(binding.meFragmentContainer.id, LoadingFragment()).commit()

        if(ConnectivityService.isOnline(requireContext())){
            loadPortfolioContent(meViewModel)
        }else{
            childFragmentManager.beginTransaction().replace(binding.meFragmentContainer.id, OfflineFragment.newInstance { retryConnexion(meViewModel) }).commit()
        }

        return root
    }

    fun retryConnexion(meViewModel: MeViewModel){
        if(ConnectivityService.isOnline(requireContext())){
            loadPortfolioContent(meViewModel)
        }
    }

    fun loadPortfolioContent(meViewModel: MeViewModel){
        meViewModel.isConnected(requireContext()).observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                childFragmentManager.beginTransaction()
                    .replace(binding.meFragmentContainer.id, ConnectedMeFragment()).commit()
            }else{
                childFragmentManager.beginTransaction().replace(binding.meFragmentContainer.id, LoggedOutFragment()).commit()

            }
        }
    }

}