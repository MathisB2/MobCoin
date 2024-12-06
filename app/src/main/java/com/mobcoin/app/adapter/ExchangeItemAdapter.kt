package com.mobcoin.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.mobcoin.app.R
import com.mobcoin.app.model.DisplayedAsset
import com.mobcoin.app.model.Ticker
import com.mobcoin.app.services.CoinService
import com.squareup.picasso.Picasso

class ExchangeItemAdapter(
    private var dataset: List<Ticker>,
    private val onEditButtonClick: (DisplayedAsset) -> Unit,
    private val context: Context
) : Adapter<ExchangeItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val exchangeIcon: ImageView = view.findViewById(R.id.exchange_Icon)
        val exchangeName: TextView = view.findViewById(R.id.exchange_name)
        val pair: TextView = view.findViewById(R.id.exchange_pair)
        val coinPrice: TextView = view.findViewById(R.id.exchange_coin_price)
        val pairVolume: TextView = view.findViewById(R.id.exchange_pair_volume)
        val exchangeRank: TextView = view.findViewById(R.id.exchange_rank)
        val exchangeButton: ImageButton = view.findViewById(R.id.exchange_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_exchangeitem, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, coinId: Int) {
        val item = dataset[coinId]

        Picasso.get().load(item.exchange.image).into(holder.exchangeIcon)
        holder.exchangeName.text = item.exchange.name
        holder.exchangeRank.text = coinId.toString()
        holder.pair.text = item.base + "/" + item.target
        holder.pairVolume.text = CoinService.formatNumber(item.volume.toDouble())
        holder.coinPrice.text = CoinService.roundDoubleToTwoDecimals(item.lastPrice.toDouble())


    }

    override fun getItemCount() = dataset.size

    fun setDataset(dataset: List<Ticker>){
        this.dataset = dataset
        this.notifyDataSetChanged()
    }

}