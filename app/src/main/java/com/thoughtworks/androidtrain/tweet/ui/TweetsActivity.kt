package com.thoughtworks.androidtrain.tweet.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.thoughtworks.androidtrain.R
import com.thoughtworks.androidtrain.tweet.TweetsViewModel
import com.thoughtworks.androidtrain.tweet.repository.TweetDatabase
import com.thoughtworks.androidtrain.tweet.repository.TweetRepositoryImpl

class TweetsActivity : AppCompatActivity() {
    private val viewModel: TweetsViewModel by viewModels {
        TweetsViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tweets)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
//        val refreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)

        viewModel.tweetsLiveData.observe(this) {
            val adapter = TweetAdapter(it)
            recyclerView.adapter = adapter
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}

class TweetsViewModelFactory(
    private val context: Context,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = TweetDatabase.dbInstance(context)
        val repository = TweetRepositoryImpl(db)
        return TweetsViewModel(repository) as T
    }
}