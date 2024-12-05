package com.mobcoin.app.adapter

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.futured.donut.DonutSection
import com.mobcoin.app.R
import com.mobcoin.app.services.CoinService

class ChartItemAdapter(
    private var dataset: List<DonutSection>,
    private var totalCount: Double
) : RecyclerView.Adapter<ChartItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coinColor: FrameLayout = view.findViewById(R.id.chart_item_color)
        val coinName: TextView = view.findViewById(R.id.chart_item_name)
        val coinValue: TextView = view.findViewById(R.id.chart_item_value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_chartitem, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, coinId: Int) {
        val item = dataset[coinId]
        holder.coinColor.setBackgroundColor(item.color)
        holder.coinName.text = item.name
        holder.coinValue.text = CoinService.roundDoubleToTwoDecimals((item.amount * 100)/totalCount) + "%"

        val drawable = GradientDrawable()
        drawable.cornerRadius = 16F
        drawable.setColor(item.color)

        holder.coinColor.background = drawable

    }

    override fun getItemCount() = dataset.size

    fun setDataset(dataset: List<DonutSection>){
        this.dataset = dataset
        this.notifyDataSetChanged()
    }

}