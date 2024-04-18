package com.example.patrol.logic.network

import com.example.patrol.logic.model.News
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CrowdService {
    @GET("mock/348963/crowd")
    fun getCrowd(@Query("lat") lat: String, @Query("lon") lon: String): Call<List<LatLng>>

}