package com.example.patrol.view.predict

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.patrol.R
import com.example.patrol.logic.Repository
import com.example.patrol.logic.model.Predictions
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class PredictActivity : AppCompatActivity() {
    private lateinit var lineChart: LineChart

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

        // Get actual data
//        val crowdData = Repository.getPrediction(lat, lon)
        // mock data
        val crowdData = ArrayList<Predictions>()
        val entries = ArrayList<Entry>()
        crowdData.add(Predictions("12:00", 10f))
        crowdData.add(Predictions("14:00", 2f))
        crowdData.add(Predictions("13:00", 7f))
        crowdData.add(Predictions("15:00", 20f))
        crowdData.add(Predictions("16:00", 16f))

        // sort data based on first element
        crowdData.sortBy { it.time }
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

        lineChart.data = LineData(lineDataSet)

        lineChart.setTouchEnabled(true)
        lineChart.isDragEnabled = true
        lineChart.setPinchZoom(true)
        lineChart.axisRight.isEnabled = false

        lineChart.description.text = "Hourly Crowd Level Forecast at $place"
        lineChart.setNoDataText("No prediction yet!")

        lineChart.animateX(1800, Easing.EaseInExpo)

        val mv = MyMarkerView(this@PredictActivity, R.layout.markertextview, lineChart, xAxisLabels)
        mv.lineChart = lineChart
        lineChart.marker = mv
        lineChart.invalidate() // refresh

    }
}