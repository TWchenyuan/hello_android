package com.thoughtworks.androidtrain.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.R
import com.thoughtworks.androidtrain.compose.theme.HelloAndroidTheme
import com.thoughtworks.androidtrain.compose.tweetlist.TweetListScreen
import com.thoughtworks.androidtrain.tweet.model.Sender
import com.thoughtworks.androidtrain.tweet.model.Tweet
import com.thoughtworks.androidtrain.util.safeToMill

class TweetListComposeActivity : ComponentActivity() {
    private val gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TweetListScreen(readFromJson())
                }
            }
        }
    }

    private fun readFromJson(): List<Tweet> {
        val jsonString = resources.openRawResource(R.raw.tweets_data).use {
            it.bufferedReader().use {
                it.readText()
            }
        }
        val tweets: List<TweetJsonItem> =
            gson.fromJson(jsonString, object : TypeToken<List<TweetJsonItem>>() {}.type)
        return tweets.mapIndexedNotNull { index, it ->
            if (it.sender == null || it.content == null) return@mapIndexedNotNull null
            val sender = Sender(
                id = "sender_${it.sender.nick}_${index}",
                userName = it.sender.username,
                nick = it.sender.nick,
                avatar = it.sender.avatar,
            )
            Tweet(
                id = "tweet_${index}",
                content = it.content,
                senderId = sender.id,
                createAt = it.date?.safeToMill() ?: System.currentTimeMillis()
            ).apply {
                this.sender = sender
            }
        }

    }
}