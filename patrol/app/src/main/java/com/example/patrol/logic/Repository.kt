package com.example.patrol.logic

import androidx.lifecycle.liveData
import com.example.patrol.logic.model.News
import com.example.patrol.logic.network.PatrolNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

object Repository {
    fun getNews(query : String?) = fire(Dispatchers.IO) {
        val multiNews = PatrolNetwork.getNews()
        Result.success(multiNews)
    }

    fun getCrowd(lat: String, lon: String) = fire(Dispatchers.IO) {
        val multiCrowds = PatrolNetwork.getCrowd(lat,lon)
        Result.success(multiCrowds)
    }

    fun getPrediction(lat: Double, lon: Double) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredPrediction = async {
                PatrolNetwork.getPrediction(lat, lon)
            }
            val multiPredictions = deferredPrediction.await()
            Result.success(multiPredictions)
        }
    }

    fun getHistroyData() = fire(Dispatchers.IO){
        val multiHistoryData = PatrolNetwork.getHistroyData()
        Result.success(multiHistoryData)
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}