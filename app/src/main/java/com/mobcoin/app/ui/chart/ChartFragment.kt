package com.mobcoin.app.ui.chart

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.mobcoin.app.R
import com.mobcoin.app.databinding.FragmentChartBinding

private const val COIN_ID_PARAM = "coinId"
private const val SHOW_CHART_INFO_PARAM = "showChartInfo"
private const val CURRENCY_PARAM = "currency"
private const val DAYS_PARAM = "days"

class ChartFragment : Fragment() {
    private var _binding: FragmentChartBinding? = null
    private var viewModel: ChartViewModel? = null
    private val binding get() = _binding!!

    private val dataset = LineDataSet(emptyList(), "DataSet 1")
    private lateinit var chart: LineChart

    private lateinit var coinId: String
    private lateinit var currency: String
    private var showChartInfo: Boolean = true
    private var days: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            coinId = it.getString(COIN_ID_PARAM)!!
            currency = it.getString(CURRENCY_PARAM)!!
            days = it.getInt(DAYS_PARAM)
            showChartInfo = it.getBoolean(SHOW_CHART_INFO_PARAM)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChartBinding.inflate(inflater, container, false)

        chart = binding.lineChart

        chart.setTouchEnabled(showChartInfo)
        chart.dragDecelerationFrictionCoef = 0.9f

        // enable scaling and dragging
        chart.isDragEnabled = showChartInfo
        chart.setScaleEnabled(true)
        chart.setDrawGridBackground(showChartInfo)
        chart.isHighlightPerDragEnabled = true

        chart.setBackgroundColor(Color.TRANSPARENT)
        chart.setGridBackgroundColor(Color.TRANSPARENT)

        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        chart.axisRight.isEnabled = false

        val textColor = ContextCompat.getColor(requireContext(), R.color.md_theme_onSurfaceVariant)
        val xAxis = chart.xAxis
        xAxis.isEnabled = showChartInfo
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textSize = 10f
        xAxis.textColor = Color.WHITE
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(false)
        xAxis.textColor = textColor
        xAxis.granularity = 1f // one hour
        xAxis.valueFormatter = HourValueFormatter()

        val leftAxis = chart.axisLeft
        leftAxis.isEnabled = showChartInfo
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.setDrawGridLines(true)
        leftAxis.setDrawAxisLine(false)
        leftAxis.isGranularityEnabled = true
        leftAxis.textColor = textColor

        val dataSetColor = ContextCompat.getColor(requireContext(), R.color.colorSuccess)
        dataset.axisDependency = AxisDependency.LEFT
        dataset.color = dataSetColor
        dataset.valueTextColor = dataSetColor
        dataset.lineWidth = 1.5f
        dataset.setDrawCircles(false)
        dataset.setDrawValues(false)
        dataset.fillAlpha = 65
        dataset.fillColor = dataSetColor

        dataset.setDrawCircleHole(false)

        viewModel = ViewModelProvider(this)[ChartViewModel::class.java]
        this.updateCoinData()

        return binding.root
    }

    fun setDays(days: Int) {
        this.days = days

        chart.xAxis.granularity = 1f // one hour
        if(days>1){
            chart.xAxis.valueFormatter = DateValueFormatter()
        }else{
            chart.xAxis.valueFormatter = HourValueFormatter()
        }

        this.updateCoinData()
    }

    private fun updateCoinData(){
        binding.lineChart.visibility = View.GONE
        binding.chartLoadingAnimationContainer.visibility = View.VISIBLE
        viewModel?.getCoinPrices(coinId, currency, days)?.observe(viewLifecycleOwner){
            this.setData(it)
            binding.lineChart.visibility = View.VISIBLE
            binding.chartLoadingAnimationContainer.visibility = View.GONE
        }
    }

    private fun setData(values: List<Entry>) {
        dataset.values = values
        chart.data = LineData(dataset)

        chart.notifyDataSetChanged()
        chart.invalidate()
    }

    companion object {
        @JvmStatic
        fun newInstance(coinId: String, currency: String, days: Int = 1, showChartInfo: Boolean = true, precision: String? = null) =
            ChartFragment().apply {
                arguments = Bundle().apply {
                    putString(COIN_ID_PARAM, coinId)
                    putString(CURRENCY_PARAM, currency)
                    putInt(DAYS_PARAM, days)
                    putBoolean(SHOW_CHART_INFO_PARAM, showChartInfo)
                }
            }
    }
}