package com.example.patrol.view.charts
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.patrol.R
import com.example.patrol.logic.Repository
import com.example.patrol.logic.model.DailyCovidNumber
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.BarLineChartBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import java.util.*


class BarChartActivity : AppCompatActivity() {
    private lateinit var barChart: BarChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart)
        barChart = findViewById<BarChart>(R.id.idBarChart)
        observeDataChanges()
    }

    private fun observeDataChanges() {
        Repository.getHistroyData().observe(this){ data ->
            data.getOrNull().let {
                if (it != null) {
                    displayBarChart(it)
                }
            }
        }
    }

    private fun displayBarChart(dailyCovidNumbers: List<DailyCovidNumber?>) {
        val barEntries = mutableListOf<BarEntry>()
        val dates = mutableListOf<String>()
        dailyCovidNumbers.forEachIndexed { index, dailyCovidNumber ->
            if (dailyCovidNumber != null) {
                barEntries.add(BarEntry(index.toFloat(), dailyCovidNumber.number))
            }
            if (dailyCovidNumber != null) {
                dates.add(dailyCovidNumber.date)
            }
        }

        val barDataSet = BarDataSet(barEntries, "Data")
        barDataSet.color = Color.BLUE
        barDataSet.valueTextSize = 20f // Set text size for data values
        val barData = BarData(barDataSet)


        // Customize x-axis labels
        val xAxis: XAxis = barChart.getXAxis()
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setGranularity(1f) // only intervals of 1 day
        xAxis.textSize = 18f
        xAxis.setLabelCount(5)
        xAxis.valueFormatter = IndexAxisValueFormatter(dates)

        // Add animation
        barChart.animateY(1500, Easing.EaseInOutQuart)
        barChart.setTouchEnabled(true)
        barChart.setDragEnabled(true)
        barChart.setScaleEnabled(true)

        barChart.data = barData
        barChart.invalidate()
    }


    class DayAxisValueFormatter(private val chart: BarLineChartBase<*>) : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return "MM-DD"
        }
    }


}
