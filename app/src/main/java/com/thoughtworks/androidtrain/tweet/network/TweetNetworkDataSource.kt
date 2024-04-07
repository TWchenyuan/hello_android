package com.thoughtworks.androidtrain.tweet.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://raw.githubusercontent.com"

class TweetNetworkDataSource {
    private val client = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun tweetApi(): TweetService {
        return client.create(TweetService::class.java)
    }
}