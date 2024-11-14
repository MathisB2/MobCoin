package com.mobcoin.app.ui.chart

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.Utils
import com.mobcoin.app.databinding.FragmentChartBinding
import com.mobcoin.app.ui.CoinInfoViewModel
import java.util.Currency
import java.util.concurrent.TimeUnit

private const val COIN_ID_PARAM = "coinId"
private const val CURRENCY_PARAM = "currency"
private const val DAYS_PARAM = "days"

class ChartFragment : Fragment() {
    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding!!

    private val dataset = LineDataSet(emptyList(), "DataSet 1")
    private lateinit var chart: LineChart

    private var coinId: String? = null
    private var currency: String? = null
    private var days: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            coinId = it.getString(COIN_ID_PARAM)
            currency = it.getString(CURRENCY_PARAM)
            days = it.getString(DAYS_PARAM)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChartBinding.inflate(inflater, container, false)

        chart = binding.lineChart

        chart.setTouchEnabled(true)
        chart.dragDecelerationFrictionCoef = 0.9f

        // enable scaling and dragging
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)
        chart.setDrawGridBackground(true)
        chart.isHighlightPerDragEnabled = true

        chart.setBackgroundColor(Color.TRANSPARENT)

        chart.description.isEnabled = false
        chart.legend.isEnabled = false

        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textSize = 10f
        xAxis.textColor = Color.WHITE
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(true)
        xAxis.textColor = Color.rgb(255, 192, 56)
        xAxis.granularity = 1f // one hour

        xAxis.valueFormatter = DateValueFormatter()

        val leftAxis = chart.axisLeft
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.textColor = ColorTemplate.getHoloBlue()
        leftAxis.setDrawGridLines(true)
        leftAxis.isGranularityEnabled = true
        leftAxis.textColor = Color.rgb(255, 192, 56)

        chart.axisRight.isEnabled = false

        dataset.axisDependency = AxisDependency.LEFT
        dataset.color = ColorTemplate.getHoloBlue()
        dataset.valueTextColor = ColorTemplate.getHoloBlue()
        dataset.lineWidth = 1.5f
        dataset.setDrawCircles(false)
        dataset.setDrawValues(false)
        dataset.fillAlpha = 65
        dataset.fillColor = ColorTemplate.getHoloBlue()
        dataset.highLightColor = Color.rgb(244, 117, 117)
        dataset.setDrawCircleHole(false)

        val coinInfoViewModel = ViewModelProvider(this)[CoinInfoViewModel::class.java]
        coinInfoViewModel.getCoinPrices(coinId!!, currency!!, days!!).observe(viewLifecycleOwner){
            this.setData(it)
        }

        return binding.root
    }

    fun setRandomData(count: Int, range: Float) {
        val now = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis())

        val values = ArrayList<Entry>()
        val to = (now + count).toFloat()
        var x = now.toFloat()

        while (x < to) {
            val y: Float = getRandom(range, 50f)
            values.add(Entry(x, y)) // add one entry per hour
            x++
        }
        this.setData(values)
    }

    fun setData(values: List<Entry>) {
        Utils.init(requireContext())
        dataset.values = values
        chart.data = LineData(dataset)
    }

    private fun getRandom(range: Float, start: Float): Float {
        return (Math.random() * range).toFloat() + start
    }

    companion object {

        @JvmStatic
        fun newInstance(coinId: String, currency: String, days: String, precision: String? = null) =
            ChartFragment().apply {
                arguments = Bundle().apply {
                    putString(COIN_ID_PARAM, coinId)
                    putString(CURRENCY_PARAM, currency)
                    putString(DAYS_PARAM, days)
                }
            }
    }
}