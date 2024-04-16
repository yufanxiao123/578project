package com.example.patrol.logic

import androidx.lifecycle.liveData
import com.example.patrol.logic.model.News
import com.example.patrol.logic.network.PatrolNetwork
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object Repository {
    fun getNews(query : String?) = fire(Dispatchers.IO) {
        val multiNews = PatrolNetwork.getNews()
        Result.success(multiNews)
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