package com.example.patrol.view.crowd

import androidx.lifecycle.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.patrol.logic.model.News
import com.example.patrol.logic.Repository

class CrowdViewModel : ViewModel() {

    data class Location(
        val lat: String,
        val lon: String
    )

    private val locationLiveData = MutableLiveData<Location>()

//    val newsList = ArrayList<News>()

    val crowdLiveData = locationLiveData.switchMap { location ->
        Repository.getCrowd(location.lat, location.lon)
    }

    fun getCrowd(lat: String, lon: String) {
        locationLiveData.value = Location(lat,lon)
    }
}