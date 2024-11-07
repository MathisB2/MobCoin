package com.mobcoin.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.mobcoin.app.R
import com.mobcoin.app.model.Coin
import com.mobcoin.app.model.search.SearchCoin
import com.mobcoin.app.utils.CoinUtils
import com.squareup.picasso.Picasso

class SearchCoinItemAdapter(
    private val context: Context,
    private var dataset: List<SearchCoin>
) : Adapter<SearchCoinItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val coinSymbole: TextView = view.findViewById(R.id.searchCoinSymbole)
        val coinName: TextView = view.findViewById(R.id.searchCoinName)
        val coinRank: TextView = view.findViewById(R.id.searchCoinRank)
        val coinIcon: ImageView = view.findViewById(R.id.searchCoinIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_searchcoinitem, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, coinId: Int) {
        val item = dataset[coinId]
        holder.coinSymbole.text = item.symbol.uppercase()
        holder.coinName.text = item.name
        holder.coinRank.text = item.marketCapRank.toString()

        Picasso.get().load(item.coinIcon).into(holder.coinIcon)

    }

    override fun getItemCount() = dataset.size

    fun setDataset(dataset: List<SearchCoin>){
        this.dataset = dataset
        notifyDataSetChanged()
    }
}