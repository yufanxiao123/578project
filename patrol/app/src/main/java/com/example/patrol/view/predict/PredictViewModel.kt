package com.example.patrol.view.predict

import androidx.lifecycle.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.patrol.logic.Repository

class PredicitViewModel : ViewModel() {

    data class Location(
        val lat: String,
        val lon: String
    )
    private val locationLiveData = MutableLiveData<Location>()

    val predictLiveData =  locationLiveData.switchMap { location ->
        Repository.getPrediction(location.lat, location.lon)
    }

    fun getPredict(lat: String, lon: String) {
        locationLiveData.value = Location(lat, lon)
    }
}