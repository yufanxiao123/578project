package com.example.patrol.view.predict

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.patrol.R
import com.example.patrol.logic.model.Prediction
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class PredictActivity : AppCompatActivity() {
    private lateinit var lineChart: LineChart
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_predict)
        val place = intent.getStringExtra("place")
        val lat = intent.getDoubleExtra("lat", 0.0)
        val lon = intent.getDoubleExtra("lon", 0.0)

        lineChart = findViewById(R.id.myLineChart)
        lineChart.setDrawGridBackground(false)
        lineChart.description.isEnabled = false
        lineChart.setPinchZoom(true)
        lineChart.setDrawMarkers(true)
        lineChart.setTouchEnabled(true)
        lineChart.isDragEnabled = true
        lineChart.setPinchZoom(true)
        lineChart.axisRight.isEnabled = false
        lineChart.description.text = "Hourly Crowd Level Forecast at $place"
        lineChart.setNoDataText("No prediction yet!")
        lineChart.animateX(1800, Easing.EaseInExpo)

        swipeRefresh = findViewById(R.id.sw_refresh)
        swipeRefresh.setColorSchemeResources(R.color.colorDark)
        refreshData(lat, lon)
        swipeRefresh.setOnRefreshListener {
            refreshData(lat, lon)
        }
    }

    private fun refreshData(lat: Double, lon: Double) {
        // Get actual data
//        val crowdData = Repository.getPrediction(lat, lon)
        // mock data
        val crowdData = ArrayList<Prediction>()
        crowdData.add(Prediction("12:00", 10f))
        crowdData.add(Prediction("14:00", 2f))
        crowdData.add(Prediction("13:00", 7f))
        crowdData.add(Prediction("15:00", 20f))
        crowdData.add(Prediction("16:00", 16f))
        updateChart(crowdData)
        // Hide the refresh indicator
    }

    private fun updateChart(crowdData: List<Prediction>) {
        val entries = ArrayList<Entry>()


        // sort data based on first element
        crowdData.sortedBy { it.time }
        val xAxisLabels = crowdData.map { it.time }
        // populate entries
        for (i in crowdData.indices) {
            entries.add(Entry(i.toFloat(), crowdData[i].crowdness))
        }

        val xAxis: XAxis = lineChart.xAxis
        xAxis.isEnabled = true
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        val xAxisFormatter = IndexAxisValueFormatter(xAxisLabels)
        lineChart.xAxis.valueFormatter = xAxisFormatter

        val lineDataSet = LineDataSet(entries, "Hourly Crowd Level Forecast")
        lineDataSet.color = R.color.colorDark
        lineDataSet.setDrawValues(false)
        lineDataSet.setDrawFilled(true)
        lineDataSet.lineWidth = 1f
        lineDataSet.circleRadius = 3f

        val mv = MyMarkerView(this@PredictActivity, R.layout.markertextview, lineChart, xAxisLabels)
        mv.lineChart = lineChart
        lineChart.marker = mv

        lineChart.data = LineData(lineDataSet)
        lineChart.invalidate() // refresh
        swipeRefresh.isRefreshing = false
    }
}