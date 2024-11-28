package com.mobcoin.app.ui.me

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.futured.donut.DonutProgressView
import app.futured.donut.DonutSection
import com.mobcoin.app.R
import com.mobcoin.app.adapter.AssetItemAdapter
import com.mobcoin.app.adapter.CoinItemAdapter
import com.mobcoin.app.databinding.FragmentConnectedMeBinding
import com.mobcoin.app.ui.me.addCoin.AddCoinActivity
import com.mobcoin.app.databinding.FragmentMeBinding
import com.mobcoin.app.model.DisplayedAsset
import com.mobcoin.app.services.CoinService
import com.mobcoin.app.services.ImageService
import com.mobcoin.app.ui.CoinInfoActivity
import com.mobcoin.app.ui.me.addCoin.EditCoinValueActivity
import com.mobcoin.app.ui.others.LoggedOutFragment
import java.util.Arrays


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
        val meViewModel = ViewModelProvider(this).get(MeViewModel::class.java)

        addCoinValueActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            meViewModel.fetchDisplayedAssets(requireContext())
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
        val recyclerView: RecyclerView = binding.recyclerViewAssetList
        val adapter = AssetItemAdapter(meViewModel.displayedAssets.value ?: emptyList(),{
            val intent = Intent(requireContext(), CoinInfoActivity::class.java)
            intent.putExtra("COIN_ID", it.coinId)
            startActivity(intent)
        },{
            val intent = Intent(requireContext(), EditCoinValueActivity::class.java)
            intent.putExtra("COIN_ID", it.coinId)
            addCoinValueActivityLauncher.launch(intent)
        })

        meViewModel.displayedAssets.observe(viewLifecycleOwner){
            adapter.setDataset(it ?: emptyList())
            setDonut(it ?: emptyList())
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        meViewModel.fetchDisplayedAssets(requireContext())

        meViewModel.accountValue.observe(viewLifecycleOwner){
            binding.accountValue.text =  "$"+ CoinService.roundDoubleToTwoDecimals(it)
        }

        return root
    }

    fun setDonut(assets : List<DisplayedAsset>){
        if(assets.isEmpty()){
            return
        }
        val donut: DonutProgressView = binding.accountDonut

        val colorList= Arrays.asList(R.color.donut_1,R.color.donut_2,R.color.donut_3,R.color.donut_4)

        var priceList = assets.take(4).mapIndexed { index, asset ->
            DonutSection(asset.coinName, ContextCompat.getColor(requireContext(), colorList[index]), (asset.coinPrice * asset.quantity).toFloat())
        }.toMutableList()

        var otherValue = 0f
        for (i in 5..assets.size){
            otherValue += (assets[i-1].coinPrice * assets[i-1].quantity).toFloat()
        }
        priceList.add(DonutSection("Others", ContextCompat.getColor(requireContext(), R.color.md_theme_primary), otherValue))

        donut.cap = 1f
        donut.submitData(priceList)
    }
}