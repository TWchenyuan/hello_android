package com.thoughtworks.androidtrain.tweet

import androidx.lifecycle.ViewModel
import com.thoughtworks.androidtrain.tweet.model.Tweet
import kotlinx.coroutines.flow.Flow

class TweetsViewModel : ViewModel() {
    suspend fun loadTweets(): Flow<List<Tweet>> {

    }
}