package com.thoughtworks.androidtrain.tweet.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.R
import com.thoughtworks.androidtrain.tweet.TweetDatabase
import com.thoughtworks.androidtrain.tweet.model.Sender
import com.thoughtworks.androidtrain.tweet.model.Tweet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TweetRepositoryImpl(
    private val database: TweetDatabase,
    private val context: Context,
) : TweetRepository {
    private val gson = Gson()
    private val tweetDao = database.tweetDao()
    private val senderDao = database.senderDao()
    override fun fetchTweets(): Flow<List<Tweet>> {
        setupTweetsFromJson(context)

        return flow {
            emit(tweetDao.getTweetsWithSenders().map {
                Tweet(id = it.tweet.id, content = it.tweet.content, senderId = it.sender.id).apply {
                    sender = it.sender
                }
            })
        }
    }

    private fun setupTweetsFromJson(context: Context) {
        val tweetStringList = getTweetsStringList(context)
        tweetStringList.forEach {
            val content = (it["content"] ?: "") as String
            val senderHash = it["sender"] as Map<*, *>
            val sender = Sender(
                userName = senderHash["username"] as String,
                nick = senderHash["nick"] as String,
                avatar = senderHash["avatar"] as String,
            )
            val tweet = Tweet(content = content, senderId = sender.id)

            senderDao.insertSender(sender)
            tweetDao.insertTweet(tweet)
        }
    }

    private fun getTweetsStringList(context: Context): List<Map<String, Any>> {
        return try {
            val jsonString = context.resources.openRawResource(R.raw.tweets_data).use {
                it.bufferedReader().use {
                    it.readText()
                }
            }
            gson.fromJson<List<Map<String, Any>>?>(
                jsonString,
                object : TypeToken<List<Map<String, Any>>>() {}.type
            )
                .filter { !it.containsKey("error") && it.containsKey("sender") }
        } catch (_: Exception) {
            emptyList()
        }
    }
}