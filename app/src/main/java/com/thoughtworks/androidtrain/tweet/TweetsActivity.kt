package com.thoughtworks.androidtrain.tweet

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.thoughtworks.androidtrain.R
import com.thoughtworks.androidtrain.tweet.model.Tweet
import com.thoughtworks.androidtrain.tweet.repository.TweetDatabase
import com.thoughtworks.androidtrain.tweet.repository.TweetRepository
import com.thoughtworks.androidtrain.tweet.repository.TweetRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TweetsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tweets)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val viewModel = ViewModelProvider(this)[TweetsViewModel::class.java]
        lifecycleScope.launch {
            val tweets = withContext(Dispatchers.IO) {
                val db = TweetDatabase.dbInstance(applicationContext)
                db.clearAllTables()
                val tweetRepository: TweetRepository = TweetRepositoryImpl(db)
                try {
                    tweetRepository.fetchTweets().first()
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@TweetsActivity, "Network Error:${e.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                    emptyList()
                }
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
}