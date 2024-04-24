package com.example.patrol.logic.network

import com.example.patrol.logic.model.PredictResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PredictService {

    @GET("mock/348963/crowd")
    fun getPrediction(@Query("lat") lat: Double, @Query("lon") lon: Double): Call<PredictResponse>
}