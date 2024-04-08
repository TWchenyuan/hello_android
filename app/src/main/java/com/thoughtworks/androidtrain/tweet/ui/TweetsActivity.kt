package com.thoughtworks.androidtrain.tweet.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.thoughtworks.androidtrain.R
import com.thoughtworks.androidtrain.tweet.TweetsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TweetsActivity : AppCompatActivity() {

    private val viewModel: TweetsViewModel by viewModels()
    private val recyclerView = lazy {
        findViewById<RecyclerView>(R.id.recycler_view)
    }
    private val adapter = TweetAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tweets)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupView()
        setupRefresh()
        viewModel.tweetsLiveData.observe(this) {
            adapter.updateTweets(it)
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupView() {
        recyclerView.value.layoutManager = LinearLayoutManager(this)
        recyclerView.value.adapter = this.adapter
    }

    private fun setupRefresh() {
        val refreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)
        refreshLayout.setOnRefreshListener {
            recyclerView.value.adapter?.notifyDataSetChanged()
            Handler(Looper.getMainLooper()).postDelayed(1000) {
                viewModel.loadTweets()
                refreshLayout.isRefreshing = false
            }
        }
    }
}
