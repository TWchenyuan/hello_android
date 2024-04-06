package com.thoughtworks.androidtrain.tweet.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.R
import com.thoughtworks.androidtrain.tweet.TweetDatabase
import com.thoughtworks.androidtrain.tweet.model.Sender
import com.thoughtworks.androidtrain.tweet.model.Tweet
import com.thoughtworks.androidtrain.tweet.model.TweetJson
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
        updateTweets()

        return flow {
            emit(tweetDao.getTweetsWithSenders().map {
                Tweet(id = it.tweet.id, content = it.tweet.content, senderId = it.sender.id).apply {
                    sender = it.sender
                }
            })
        }
    }

    data class ValidTweet(
        val content: String,
        val sender: TweetJson.TweetSender,
        val images: List<TweetJson.TweetImage>,
        val comments: List<TweetJson.TweetComments>,
    )

    private fun updateTweets() {
        getTweetsStringList()
            .mapNotNull {
                if (it.content == null || it.sender == null) null else ValidTweet(
                    it.content,
                    it.sender,
                    it.images ?: emptyList(),
                    it.comments ?: emptyList()
                )
            }
            .forEach {
                val sender = Sender(
                    userName = it.sender.username,
                    nick = it.sender.nick,
                    avatar = it.sender.avatar,
                )
                val tweet = Tweet(content = it.content, senderId = sender.id)

                senderDao.insertSender(sender)
                tweetDao.insertTweet(tweet)
            }
    }

    private fun getTweetsStringList(): List<TweetJson> {
        return try {
            val jsonString = context.resources.openRawResource(R.raw.tweets_data).use {
                it.bufferedReader().use {
                    it.readText()
                }
            }
            gson.fromJson(
                jsonString,
                object : TypeToken<List<TweetJson>>() {}.type
            )
        } catch (_: Exception) {
            emptyList()
        }
    }
}