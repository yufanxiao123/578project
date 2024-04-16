package com.example.patrol.view.news

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.patrol.R
import com.example.patrol.logic.model.News

class NewsActivity : ComponentActivity() {
    private lateinit var newsAdapter: NewsAdapter

    // cgz added- viewModel
    private val viewModel by lazy { ViewModelProvider(this).get(NewsViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        newsAdapter = NewsAdapter(mutableListOf())

        // cgz added
        viewModel.getNews(null)
        viewModel.newsLiveData.observe(this, Observer{ result ->
            val news = result.getOrNull()
            if (news != null) {
                for (new in news){
                    newsAdapter.newsList.add(new)
                }

                val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_news_items)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = newsAdapter

            } else {
                Toast.makeText(this, "no news found", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

// yufanxiao's original code
//        newsAdapter.newsList.add(News("News 1", "https://ca.cair.com/losangeles/wp-content/uploads/sites/6/2020/03/CAIR_CA-COVID_Insta.jpg", "Content 1"))
//        newsAdapter.newsList.add(News("News 2", "https://ca.cair.com/losangeles/wp-content/uploads/sites/6/2020/03/CAIR_CA-COVID_Insta.jpg", "Content 2"))
//        newsAdapter.newsList.add(News("News 3", "https://ca.cair.com/losangeles/wp-content/uploads/sites/6/2020/03/CAIR_CA-COVID_Insta.jpg", "Content 3"))

//        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_news_items)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = newsAdapter

    }
}