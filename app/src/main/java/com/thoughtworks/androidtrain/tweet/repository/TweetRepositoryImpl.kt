package com.thoughtworks.androidtrain.tweet.repository

import com.thoughtworks.androidtrain.tweet.model.Sender
import com.thoughtworks.androidtrain.tweet.model.Tweet
import com.thoughtworks.androidtrain.tweet.network.TweetModel
import com.thoughtworks.androidtrain.tweet.network.TweetNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TweetRepositoryImpl(
    private val database: TweetDatabase,
) : TweetRepository {
    private val tweetDao = database.tweetDao()
    private val senderDao = database.senderDao()
    private val api = TweetNetworkDataSource().tweetApi()
    override suspend fun fetchTweets(): Flow<List<Tweet>> {
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
        val sender: TweetModel.TweetSender,
        val images: List<TweetModel.TweetImage>,
        val comments: List<TweetModel.TweetComments>,
    )

    private suspend fun updateTweets() {
        this.api.getTweets()
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
}