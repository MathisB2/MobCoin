package com.mobcoin.app.ui.others

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.mobcoin.app.R
import com.mobcoin.app.databinding.FragmentLoggedOutBinding
import com.mobcoin.app.ui.login.LoginActivity


private const val TEXT_MESSAGE_PARAM = "textMessage"

class LoggedOutFragment : Fragment() {
    private var textMessage: String? = null

    private var _binding: FragmentLoggedOutBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            textMessage = it.getString(TEXT_MESSAGE_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoggedOutBinding.inflate(inflater, container, false)
        val root: View = binding.root


        if(textMessage != null){
            binding.loggedOutFragmentGuestMessage.text = textMessage
        }else{
            binding.loggedOutFragmentGuestMessage.text = getString(R.string.meFragment_guestMessage)
        }

        val loginButton: Button = binding.loggedOutFragmentLoginButton
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


    companion object {
        @JvmStatic
        fun newInstance(textMessage: String?) =
            LoggedOutFragment().apply {
                arguments = Bundle().apply {
                    putString(TEXT_MESSAGE_PARAM, textMessage)
                }
            }
    }
}