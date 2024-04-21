package com.example.patrol.view.predict

import android.content.Context
import android.widget.TextView
import com.example.patrol.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF


class MyMarkerView(context: Context, layoutResource: Int, var lineChart: LineChart, val xAxisLabels:List<String>) : MarkerView(context, layoutResource) {
    private val tvContent: TextView = findViewById(R.id.tvContent1)

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        val lineData = lineChart.lineData
        val dataSet = lineData.getDataSetByIndex(0)
        val index = dataSet.getEntryIndex(e)
        tvContent.text = "time: ${xAxisLabels[index]} \n crowdness: ${e?.y}"
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-(width / 2).toFloat(), -height.toFloat());
    }

}