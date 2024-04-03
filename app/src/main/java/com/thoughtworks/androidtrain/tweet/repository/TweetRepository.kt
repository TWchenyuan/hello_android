package com.thoughtworks.androidtrain.tweet.repository

import android.content.Context
import com.thoughtworks.androidtrain.tweet.model.Tweet
import kotlinx.coroutines.flow.Flow

interface TweetRepository {
    fun fetchTweets(context: Context): Flow<List<Tweet>>
}