package com.example.patrol.logic.network

import com.example.patrol.logic.model.News
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CrowdService {
//    mock/348963/crowd
    @GET("crowd")
    fun getCrowd(@Query("lat") lat: String, @Query("long") lon: String): Call<List<LatLng>>

}