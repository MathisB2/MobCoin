package com.mobcoin.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.mobcoin.app.R
import com.mobcoin.app.model.Coin
import com.mobcoin.app.model.DetailedCoin
import com.mobcoin.app.services.CoinService
import com.mobcoin.app.ui.chart.ChartFragment
import com.squareup.picasso.Picasso
import kotlin.math.log

class FavoriteCoinItemAdapter(
    private var dataset: List<DetailedCoin>,
    private val fragmentManager: FragmentManager,
    private val onItemClick: (DetailedCoin) -> Unit
) : Adapter<FavoriteCoinItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coinName: TextView = view.findViewById(R.id.favorite_item_coin_name)
        val coinEvolution: TextView = view.findViewById(R.id.favorite_item_coin_evolution)
        val coinIcon: ImageView = view.findViewById(R.id.favorite_item_coin_icon)
        val coinChart: FragmentContainerView = view.findViewById<FragmentContainerView?>(R.id.favorite_coin_chart).apply {
            id = View.generateViewId()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view

        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, coinId: Int) {
        val item = dataset[coinId]
        holder.coinName.text = item.name.uppercase()

        val chart = ChartFragment.newInstance(item.id, "usd", 1, false)
        fragmentManager.beginTransaction().replace(holder.coinChart.id, chart).commit()

        CoinService.setPercentageText(item.marketData?.percentagePriceChange24h ?: 0.0, holder.coinEvolution)
        Picasso.get().load(item.getImageUrlSmall()).into(holder.coinIcon)

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount() = dataset.size

    fun setDataset(dataset: List<DetailedCoin>){
        this.dataset = dataset
        notifyDataSetChanged()
    }
}