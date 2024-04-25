package com.example.patrol.logic.network

import com.example.patrol.logic.model.Prediction
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PredictService {

    @GET("predict/predict")
    fun getPrediction(@Query("lat") lat: String, @Query("long") lon: String): Call<List<Prediction>>
}