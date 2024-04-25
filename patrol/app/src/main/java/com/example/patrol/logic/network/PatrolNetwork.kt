package com.example.patrol.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object PatrolNetwork {
    private val newsService = ServiceCreator.create(NewsService::class.java)

    suspend fun getNews() = newsService.getNews().await()

    private val crowdService = ServiceCreator.create(CrowdService::class.java)
    suspend fun getCrowd(lat: String, lon: String) = crowdService.getCrowd(lat,lon).await()

    private val predictService = ServiceCreator.create(PredictService::class.java)
    suspend fun getPrediction(lat: String, lon: String) = predictService.getPrediction(lat,lon).await()

    private val historyDataService = ServiceCreator.create(HistoryDataService::class.java)
    suspend fun getHistroyData() = historyDataService.getHistroyData().await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}