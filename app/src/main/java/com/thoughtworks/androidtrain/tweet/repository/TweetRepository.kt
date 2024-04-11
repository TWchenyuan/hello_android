package com.thoughtworks.androidtrain.tweet.repository

import com.thoughtworks.androidtrain.tweet.model.Tweet
import kotlinx.coroutines.flow.Flow

interface TweetRepository {
    fun fetchTweets(): Flow<List<Tweet>>

    suspend fun loadTweets(): List<Tweet>
}
