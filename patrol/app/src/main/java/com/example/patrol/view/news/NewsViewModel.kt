package com.example.patrol.view.news

import androidx.lifecycle.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.patrol.logic.model.News
import com.example.patrol.logic.Repository

class NewsViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String?>()

    val newsList = ArrayList<News>()

    val newsLiveData = searchLiveData.switchMap { query ->
        Repository.getNews(query)
    }

    fun getNews(query: String? = null) {
        searchLiveData.value = query
    }
}