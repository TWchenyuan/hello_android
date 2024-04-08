package com.thoughtworks.androidtrain.tweet

import androidx.lifecycle.ViewModel
import com.thoughtworks.androidtrain.tweet.model.Tweet
import com.thoughtworks.androidtrain.tweet.repository.TweetRepository
import kotlinx.coroutines.flow.Flow

class TweetsViewModel(
    private val repository: TweetRepository,
) : ViewModel() {
    suspend fun loadTweets(): Flow<List<Tweet>> {
        return repository.fetchTweets()
    }
}