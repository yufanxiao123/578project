package com.example.patrol.view.charts
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart)
        val barChart = findViewById<BarChart>(R.id.idBarChart)

        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, 20f))
        entries.add(BarEntry(1f, 30f))
        entries.add(BarEntry(2f, 40f))
        entries.add(BarEntry(3f, 50f))
        entries.add(BarEntry(4f, 60f))

        val barDataSet = BarDataSet(entries, "Bar Data Set")
        barDataSet.color = Color.BLUE

        val barData = BarData(barDataSet)
        barChart.data = barData

        barChart.data.setValueTextSize(18f)

        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.textSize = 20f

        val labels = ArrayList<String>()
        labels.add("01-01")
        labels.add("01-02")
        labels.add("01-03")
        labels.add("01-04")
        labels.add("01-05")

        xAxis.valueFormatter = IndexAxisValueFormatter(labels)

        barChart.setFitBars(true)
        barChart.animateY(1500)
        barChart.invalidate()
    }

}