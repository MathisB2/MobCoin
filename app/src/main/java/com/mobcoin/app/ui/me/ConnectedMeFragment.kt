package com.mobcoin.app.ui.me

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.futured.donut.DonutProgressView
import app.futured.donut.DonutSection
import com.google.android.material.button.MaterialButton
import com.mobcoin.app.R
import com.mobcoin.app.adapter.AssetItemAdapter
import com.mobcoin.app.adapter.ChartItemAdapter
import com.mobcoin.app.databinding.FragmentConnectedMeBinding
import com.mobcoin.app.model.Currency
import com.mobcoin.app.model.DisplayedAsset
import com.mobcoin.app.services.CoinService
import com.mobcoin.app.services.CurrencyService
import com.mobcoin.app.services.ImageService
import com.mobcoin.app.ui.CoinInfoActivity
import com.mobcoin.app.ui.me.addCoin.AddCoinActivity
import com.mobcoin.app.ui.me.addCoin.EditCoinValueActivity
import com.mobcoin.app.ui.me.settings.SettingsActivity


class ConnectedMeFragment : Fragment() {

    private var _binding: FragmentConnectedMeBinding? = null

    private val binding get() = _binding!!

    private lateinit var addCoinValueActivityLauncher: ActivityResultLauncher<Intent>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConnectedMeBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val meViewModel = ViewModelProvider(this)[MeViewModel::class.java]

        addCoinValueActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            meViewModel.fetchDisplayedAssets(requireContext())
        }

        val settingsButton: MaterialButton = binding.meConnectedSettingsButton
        settingsButton.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(intent)
        }

        val plusButton: ImageButton = binding.buttonConnectedMe
        plusButton.setOnClickListener {
            val intent = Intent(requireContext(), AddCoinActivity::class.java)
            addCoinValueActivityLauncher.launch(intent)
        }


        meViewModel.getUser(requireContext()).observe(viewLifecycleOwner) {
            binding.meConnectedUsername.text = it.surname

            if(it.profileImage != null){
                val userBitmap = ImageService.byteArrayToBitmap(it.profileImage)
                binding.meConnectedProfilePicture.setImageBitmap(userBitmap)
            }   

        }

        // Asset List
        val recyclerViewAsset: RecyclerView = binding.recyclerViewAssetList
        val adapter = AssetItemAdapter(meViewModel.displayedAssets.value ?: emptyList(),{
            val intent = Intent(requireContext(), CoinInfoActivity::class.java)
            intent.putExtra("COIN_ID", it.coinId)
            startActivity(intent)
        },{
            val intent = Intent(requireContext(), EditCoinValueActivity::class.java)
            intent.putExtra("COIN_ID", it.coinId)
            addCoinValueActivityLauncher.launch(intent)
        },
            requireContext())

        meViewModel.displayedAssets.observe(viewLifecycleOwner){
            adapter.setDataset(it ?: emptyList())
            setDonut(it ?: emptyList())
            setPortfolioChange(it ?: emptyList())
        }

        recyclerViewAsset.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewAsset.adapter = adapter
        meViewModel.fetchDisplayedAssets(requireContext())

        meViewModel.accountValue.observe(viewLifecycleOwner){
            binding.accountValue.text = CoinService.roundDoubleToTwoDecimals(it) + Currency.getCurrencySymbole(CurrencyService.getCurrency(requireContext()))
        }

        return root
    }

    private fun setDonut(assets : List<DisplayedAsset>){
        if(assets.isEmpty()){
            return
        }
        val donut: DonutProgressView = binding.accountDonut

        val colorList= listOf(R.color.donut_1,R.color.donut_2,R.color.donut_3,R.color.donut_4)

        val priceList = assets.take(4).mapIndexed { index, asset ->
            DonutSection(asset.coinName, ContextCompat.getColor(requireContext(), colorList[index]), (asset.coinPrice * asset.quantity).toFloat())
        }.toMutableList()

        var otherValue = 0f
        for (i in 5..assets.size){
            otherValue += (assets[i-1].coinPrice * assets[i-1].quantity).toFloat()
        }
        if(otherValue > 0){
            priceList.add(DonutSection("Others", ContextCompat.getColor(requireContext(), R.color.md_theme_primary), otherValue))
        }
        donut.cap = 1f
        donut.submitData(priceList)


        setChartTitles(priceList)
    }

    private fun setChartTitles(donnutSections: List<DonutSection>){

        var totalCount = 0.0
        for (section in donnutSections){
            totalCount += section.amount
        }

        val recyclerViewChart: RecyclerView = binding.recyclerViewCoinChart
        val adapter = ChartItemAdapter(donnutSections, totalCount)
        recyclerViewChart.layoutManager = LinearLayoutManager(requireContext())

        recyclerViewChart.adapter = adapter
    }

    fun setPortfolioChange(assets: List<DisplayedAsset>){
        var totalInvested = 0.0
        var totalChange = 0.0

        for (asset in assets) {
            totalInvested += asset.quantity * asset.coinPrice
            totalChange += asset.quantity * asset.coinPrice * asset.coinChange
        }

        val percent = if(totalInvested == 0.0) 0.0 else totalChange/totalInvested
        val value = if(totalChange == 0.0) 0.0 else totalInvested/totalChange

        CoinService.setPercentageText(percent, binding.textViewPortfolioPercent)
        binding.textViewPortfolioPercent.setText(binding.textViewPortfolioPercent.text.toString() + "(24h)")
        CoinService.setValueChangeText(value, binding.textViewPortfolioChange,requireContext())
        binding.textViewPortfolioChange.setText(binding.textViewPortfolioChange.text.toString() + "(24h)")
    }

}