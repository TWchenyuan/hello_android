package com.thoughtworks.androidtrain.tweet.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.executeAsync

const val TWEETS_URL =
    "https://raw.githubusercontent.com/TW-Android-Junior-Training/android_training_practice/main/json/tweets.json"

class TweetNetworkDataSource {
    private val client = OkHttpClient()
    private val gson = Gson()
    suspend fun fetchTweets(): List<TweetModel> {
        val request = Request.Builder()
            .url(TWEETS_URL)
            .build()
        return try {
            client.newCall(request).executeAsync().use { response ->
                withContext(Dispatchers.IO) {
                    val bodyString = response.body.string()
                    gson.fromJson(
                        bodyString,
                        object : TypeToken<List<TweetModel>>() {}.type
                    )
                }
            }
        } catch (e: Exception) {
            throw TweetNetworkErrorException()
        }
    }
}