package com.mobcoin.app.ui.internet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.mobcoin.app.databinding.FragmentOfflineBinding

class OfflineFragment(private val onButtonClicked: () -> Unit) : Fragment() {

    private var _binding: FragmentOfflineBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOfflineBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val retryButton: Button = binding.offlineFragmentRetryButton
        retryButton.setOnClickListener{
            onButtonClicked()
        }

        return root
    }

    companion object {
        fun newInstance(callback: () -> Unit): OfflineFragment {
            return OfflineFragment(callback)
        }

    }
}