package com.thoughtworks.androidtrain.tweet

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.R
import com.thoughtworks.androidtrain.tweet.model.Tweet

class TweetsActivity : AppCompatActivity() {
    val gson = Gson()
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