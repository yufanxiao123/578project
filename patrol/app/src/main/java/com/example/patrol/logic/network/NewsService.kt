package com.example.patrol.logic.network

import com.example.patrol.logic.model.News
import retrofit2.Call
import retrofit2.http.GET

interface NewsService {
//    mock/348963/newsDemo
    @GET("news")
    fun getNews(): Call<List<News>>
}