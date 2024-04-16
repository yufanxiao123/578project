package com.example.patrol.logic.network

import com.example.patrol.logic.model.News
import retrofit2.Call
import retrofit2.http.GET

interface NewsService {
    @GET("mock/348963/newsDemo")
    fun getNews(): Call<List<News>>
}