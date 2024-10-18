package com.mobcoin.app.ui.me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mobcoin.app.databinding.FragmentMeBinding
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
        val meViewModel =
            ViewModelProvider(this).get(MeViewModel::class.java)



        var logged: Boolean = false

        if(logged){
//            childFragmentManager.beginTransaction().replace(fragmentId, LoggedOutFragment()).commit()
        }else{
            childFragmentManager.beginTransaction().replace(binding.meFragmentContainer.id, LoggedOutFragment()).commit()
        }


        return root
    }

}