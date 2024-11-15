package com.mobcoin.app.ui.me

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import com.mobcoin.app.databinding.FragmentConnectedMeBinding
import com.mobcoin.app.ui.me.addCoin.AddCoinActivity

class ConnectedMeFragment : Fragment() {

    private var _binding: FragmentConnectedMeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConnectedMeBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val meViewModel = ViewModelProvider(this).get(MeViewModel::class.java)

        val plusButton: ImageButton = binding.buttonConnectedMe
        plusButton.setOnClickListener {
            val intent = Intent(requireContext(), AddCoinActivity::class.java)
            startActivity(intent)
        }


        return root
    }


}