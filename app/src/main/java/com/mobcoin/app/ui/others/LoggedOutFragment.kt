package com.mobcoin.app.ui.others

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.mobcoin.app.databinding.FragmentLoggedOutBinding
import com.mobcoin.app.ui.login.LoginActivity
import com.mobcoin.app.ui.me.MeViewModel


class LoggedOutFragment : Fragment() {

    private var _binding: FragmentLoggedOutBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoggedOutBinding.inflate(inflater, container, false)
        val root: View = binding.root



        var loginButton: Button = binding.loggedOutFragmentLoginButton
        loginButton.setOnClickListener{
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}