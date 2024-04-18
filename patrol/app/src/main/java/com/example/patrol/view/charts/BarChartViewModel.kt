package com.example.patrol.view.charts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.patrol.logic.model.DailyCovidNumber
import com.github.mikephil.charting.data.BarEntry

class BarChartViewModel: ViewModel() {
    private val _barEntries = MutableLiveData<List<BarEntry>>()
    val barEntries: LiveData<List<BarEntry>> = _barEntries

    private val _xAxisLabels = MutableLiveData<List<String>>()
    val xAxisLabels: LiveData<List<String>> = _xAxisLabels

    fun generateBarEntries() {

        var numberList = getNumberList()
        val data = mutableListOf<Float>()
        val dates = mutableListOf<String>()

        numberList.forEach{item ->
            data.add(item.number)
            dates.add(item.date)
        }

        val entries = mutableListOf<BarEntry>()

        data.forEachIndexed { index, value ->
            entries.add(BarEntry(index.toFloat(), value))
        }

        _barEntries.value = entries
        _xAxisLabels.value = dates
    }

    //从数据库读取，待开发
    fun getNumberList(): MutableList<DailyCovidNumber> {
        val dailyNumberList = mutableListOf<DailyCovidNumber>()

        dailyNumberList.add(DailyCovidNumber("01-01", 500f))
        dailyNumberList.add(DailyCovidNumber("01-02", 600f))
        dailyNumberList.add(DailyCovidNumber("01-03",700f))
        dailyNumberList.add(DailyCovidNumber("01-04", 900f))
        dailyNumberList.add(DailyCovidNumber("01-05", 1100f))

        return dailyNumberList
    }
}