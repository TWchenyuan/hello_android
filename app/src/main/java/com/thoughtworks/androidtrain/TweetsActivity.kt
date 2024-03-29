package com.thoughtworks.androidtrain

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.model.Tweet

class TweetsActivity : AppCompatActivity() {
    private val gson = Gson()
    private val adapter = TweetAdapter(tweetsFromJson())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tweets)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initialTweetList()
    }

    private fun initialTweetList() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = TweetAdapter(tweetsFromJson())
        recyclerView.adapter = adapter
        val refreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)
        refreshLayout.setOnRefreshListener {
            adapter.notifyDataSetChanged()
            Handler(Looper.getMainLooper()).postDelayed(1000) {
                adapter.rebuild()
                refreshLayout.isRefreshing = false
            }
        }
    }

    private fun initRefresh() {
    }

    private fun tweetsFromJson(): List<Tweet> {
        return try {
            val jsonString = assets.open("tweets_data.json").use {
                it.bufferedReader().use {
                    it.readText()
                }
            }
            val tweets: List<Tweet> =
                gson.fromJson(jsonString, object : TypeToken<List<Tweet>>() {}.type)
            tweets.filter { it.getError() == null && it.getSender() != null }
        } catch (e: Exception) {
            emptyList()
        }
    }
}