package com.mobcoin.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import app.futured.donut.DonutProgressView
import app.futured.donut.DonutSection
import com.mobcoin.app.R
import com.mobcoin.app.databinding.FragmentHomeBinding
import com.mobcoin.app.model.Currency
import com.mobcoin.app.services.CoinService
import com.mobcoin.app.services.ConnectivityService
import com.mobcoin.app.services.CurrencyService
import com.mobcoin.app.ui.IsOnlineHomeFragment
import com.mobcoin.app.ui.internet.OfflineFragment
import com.mobcoin.app.ui.others.LoadingFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val containerId = binding.homeFragmentContainer.id
        childFragmentManager.beginTransaction().replace(containerId, LoadingFragment()).commit()

        if(ConnectivityService.isOnline(requireContext())){
            loadData()
            //coin list fragment
            childFragmentManager.beginTransaction().replace(containerId, IsOnlineHomeFragment()).commit()
        }else{
            //offline fragment
            childFragmentManager.beginTransaction().replace(containerId, OfflineFragment.newInstance { retryConnexion() }).commit()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun retryConnexion(){
        if(ConnectivityService.isOnline(requireContext())){
            loadData()
            //coin list fragment
            val containerId = binding.homeFragmentContainer.id
            childFragmentManager.beginTransaction().replace(containerId, IsOnlineHomeFragment()).commit()
        }
    }

    fun loadData(){
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        // FNG donut
        val fngDonut: DonutProgressView = binding.fngDonut
        val fngValueText: TextView = binding.fngValue

        homeViewModel.getFNG().observe(viewLifecycleOwner){
            val fngValue = DonutSection(
                name = "FNGValue",
                color = ContextCompat.getColor(requireContext(),R.color.md_theme_primary),
                amount = it?.value?.toFloat() ?: 0f
            )
            fngDonut.cap = 100f
            fngDonut.submitData(listOf(fngValue))
            fngValueText.text = it?.value?.toString() ?: "0"
        }

        //  global market data
        val mcValueText: TextView = binding.textViewMCValue
        val mcChangeText: TextView = binding.textViewMCChange
        val volumeValueText: TextView = binding.textViewVolumeValue
        val dominanceValueText: TextView = binding.textViewDominanceValue
        val dominanceCoinText: TextView = binding.textViewDominanceCoin
        homeViewModel.getGlobalMarketData().observe(viewLifecycleOwner){
            mcValueText.text = Currency.getCurrencySymbole(CurrencyService.getCurrency(requireContext())) +
                    CoinService.formatNumber(it?.totalMarketCap?.get(CurrencyService.getCurrency(requireContext())))
            CoinService.setPercentageText(it?.marketCapChangePercentage24hUsd,mcChangeText)
            volumeValueText.text = CoinService.formatNumber(it?.totalVolume?.get(CurrencyService.getCurrency(requireContext()))) + Currency.getCurrencySymbole(CurrencyService.getCurrency(requireContext()))

            val dominanceMap: Map.Entry<String, Double>? = it?.marketCapPercentage?.maxByOrNull { it.value }

            dominanceValueText.text = CoinService.roundDoubleToTwoDecimals(dominanceMap?.value) + "%"
            dominanceCoinText.text = dominanceMap?.key?.uppercase() ?: "-"

        }
    }

}