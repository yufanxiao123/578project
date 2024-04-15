package com.example.patrol.view.news

import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.*
import com.example.patrol.R
import com.example.patrol.logic.model.News
import com.squareup.picasso.Picasso

class NewsAdapter (
    val newsList: MutableList<News> = mutableListOf()
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.tv_news_title).text = news.title
            findViewById<TextView>(R.id.tv_news_content).text = news.content
            val imageView = findViewById<ImageView>(R.id.iv_news_image)
            Picasso.get().load(news.image).into(imageView)
        }
    }
}