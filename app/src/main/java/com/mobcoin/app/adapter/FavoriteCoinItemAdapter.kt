package com.mobcoin.app.adapter

import android.annotation.SuppressLint
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
import com.mobcoin.app.utils.CoinUtils
import com.squareup.picasso.Picasso

class FavoriteCoinItemAdapter(
    private val context: Context,
    private var dataset: List<Coin>
) : Adapter<FavoriteCoinItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val coinName: TextView = view.findViewById(R.id.favorite_item_coin_name)
        val coinEvolution: TextView = view.findViewById(R.id.favorite_item_coin_evolution)
        val coinIcon: ImageView = view.findViewById(R.id.favorite_item_coin_icon)
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
        CoinUtils.setPercentageText(item.percentagePriceChange24h,holder.coinEvolution)

        Picasso.get().load(item.image).into(holder.coinIcon)
    }

    override fun getItemCount() = dataset.size

    fun setDataset(dataset: List<Coin>){
        this.dataset = dataset
        notifyDataSetChanged()
    }
}