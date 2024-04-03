package com.thoughtworks.androidtrain.tweet

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.R
import com.thoughtworks.androidtrain.tweet.model.Tweet
import com.thoughtworks.androidtrain.tweet.repository.TweetRepository
import com.thoughtworks.androidtrain.tweet.repository.TweetRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TweetsActivity : AppCompatActivity() {
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tweets)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        lifecycleScope.launch {
            val tweets = withContext(Dispatchers.IO) {
                val db = TweetDatabase.dbInstance(applicationContext)
                db.clearAllTables()
                val tweetRepository: TweetRepository = TweetRepositoryImpl(db)
                tweetRepository.fetchTweets(applicationContext).toList().first()
            }
            initialTweetList(tweets)
        }
    }

    private fun initialTweetList(tweets: List<Tweet>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = TweetAdapter(tweets)
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

    private fun tweetsFromJson(): List<Tweet> {
        return try {
            val jsonString = resources.openRawResource(R.raw.tweets_data).use {
                it.bufferedReader().use {
                    it.readText()
                }
            }
            val tweets: List<Tweet> =
                gson.fromJson(jsonString, object : TypeToken<List<Tweet>>() {}.type)
            tweets.filter { it.error == null && it.sender != null }
        } catch (e: Exception) {
            emptyList()
        }
    }
}