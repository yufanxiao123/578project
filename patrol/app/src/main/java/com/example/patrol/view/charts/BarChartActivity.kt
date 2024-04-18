package com.example.patrol.view.charts
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.patrol.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import java.util.*

class BarChartActivity : AppCompatActivity() {
    private lateinit var viewModel: BarChartViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart)
        val barChart = findViewById<BarChart>(R.id.idBarChart)

        viewModel = ViewModelProvider(this).get(BarChartViewModel::class.java)

        viewModel.barEntries.observe(this) { entries ->
            val dataSet = BarDataSet(entries, "Bar Data Set")
            val data = BarData(dataSet)
            barChart.data = data
            barChart.data.setValueTextSize(18f)

            val xAxis = barChart.xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.textSize = 18f


            barChart.setFitBars(true)
            barChart.animateY(1500)

            barChart.invalidate()
        }

        viewModel.xAxisLabels.observe(this) { labels ->
            val xAxis = barChart.xAxis
            xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        }

        viewModel.generateBarEntries()
    }


}