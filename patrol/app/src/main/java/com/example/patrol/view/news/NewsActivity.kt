package com.example.patrol.view.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.patrol.R
import com.example.patrol.logic.model.News

class NewsActivity : ComponentActivity() {
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        newsAdapter = NewsAdapter(mutableListOf())
        newsAdapter.newsList.add(News("News 1", "https://ca.cair.com/losangeles/wp-content/uploads/sites/6/2020/03/CAIR_CA-COVID_Insta.jpg", "Content 1"))
        newsAdapter.newsList.add(News("News 2", "https://ca.cair.com/losangeles/wp-content/uploads/sites/6/2020/03/CAIR_CA-COVID_Insta.jpg", "Content 2"))
        newsAdapter.newsList.add(News("News 3", "https://ca.cair.com/losangeles/wp-content/uploads/sites/6/2020/03/CAIR_CA-COVID_Insta.jpg", "Content 3"))

        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_news_items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = newsAdapter

    }
}