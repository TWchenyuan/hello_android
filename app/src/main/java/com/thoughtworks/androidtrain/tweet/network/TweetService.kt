package com.thoughtworks.androidtrain.tweet.network

import retrofit2.http.GET

interface TweetService {
    @GET("/TW-Android-Junior-Training/android_training_practice/main/json/tweets.json")
    suspend fun getTweets(): List<TweetModel>
}
