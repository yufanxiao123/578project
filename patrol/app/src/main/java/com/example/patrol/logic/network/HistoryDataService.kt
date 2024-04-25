package com.example.patrol.logic.network


import com.example.patrol.logic.model.DailyCovidNumber
import retrofit2.Call
import retrofit2.http.GET

interface HistoryDataService {
    @GET("history")
    fun getHistroyData() : Call<List<DailyCovidNumber>>
}