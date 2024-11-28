package com.mobcoin.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.mobcoin.app.R
import com.mobcoin.app.model.Coin
import com.mobcoin.app.services.CoinService
import com.squareup.picasso.Picasso

class CoinItemAdapter(
    private var dataset: List<Coin>,
    private val onItemClick: (Coin) -> Unit
) : Adapter<CoinItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coinSymbole: TextView = view.findViewById(R.id.coinSymbole)
        val coinPrice: TextView = view.findViewById(R.id.coinPrice)
        val coinChanges: TextView = view.findViewById(R.id.coinChanges)
        val coinIcon: ImageView = view.findViewById(R.id.coinIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_coinitem, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, coinId: Int) {
        val item = dataset[coinId]
        holder.coinSymbole.text = item.symbol.uppercase()
        holder.coinPrice.text = "$" + item.currentPrice.toString()
        CoinService.setPercentageText(item.percentagePriceChange24h,holder.coinChanges)

        Picasso.get().load(item.image).into(holder.coinIcon)

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }

    }

    override fun getItemCount() = dataset.size

    fun setDataset(dataset: List<Coin>){
        this.dataset = dataset
        this.notifyDataSetChanged()
    }
}