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
import com.mobcoin.app.model.DisplayedAsset
import com.mobcoin.app.services.CoinService
import com.squareup.picasso.Picasso

class AssetItemAdapter(
    private var dataset: List<DisplayedAsset>
) : Adapter<AssetItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coinIcon: ImageView = view.findViewById(R.id.coinIcon)
        val coinName: TextView = view.findViewById(R.id.textView_coin_name)
        val coinSymbol: TextView = view.findViewById(R.id.textView_coin_symbol)
        val coinPrice: TextView = view.findViewById(R.id.textView_coin_price)
        val coinChange: TextView = view.findViewById(R.id.textView_coin_change)
        val currencyQuantity: TextView = view.findViewById(R.id.textView_currency_quantity)
        val coinQuantity: TextView = view.findViewById(R.id.textView_coin_quantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_assetitem, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, coinId: Int) {
        val item = dataset[coinId]

        holder.coinName.text = item.coinName
        holder.coinSymbol.text = item.coinSymbol
        holder.coinPrice.text = "$" + item.coinPrice.toString()
        CoinService.setPercentageText(item.coinChange,holder.coinChange)
        holder.currencyQuantity.text = (CoinService.roundDoubleToTwoDecimals(item.quantity * item.coinPrice)) + " $"
        holder.coinQuantity.text = CoinService.roundDoubleToTwoDecimals(item.quantity)
        Picasso.get().load(item.coinIcon).into(holder.coinIcon)

    }

    override fun getItemCount() = dataset.size

    fun setDataset(dataset: List<DisplayedAsset>){
        this.dataset = dataset
        this.notifyDataSetChanged()
    }

}