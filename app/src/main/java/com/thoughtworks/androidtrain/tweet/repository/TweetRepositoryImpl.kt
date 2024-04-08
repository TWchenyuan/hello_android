package com.thoughtworks.androidtrain.tweet.repository

import com.thoughtworks.androidtrain.tweet.model.Sender
import com.thoughtworks.androidtrain.tweet.model.Tweet
import com.thoughtworks.androidtrain.tweet.network.TweetModel
import com.thoughtworks.androidtrain.tweet.network.TweetNetworkDataSource
import com.thoughtworks.androidtrain.util.safeToMill
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TweetRepositoryImpl(
    private val database: TweetDatabase,
) : TweetRepository {
    private val tweetDao = database.tweetDao()
    private val senderDao = database.senderDao()
    private val api = TweetNetworkDataSource().tweetApi()
    override fun fetchTweets(): Flow<List<Tweet>> {
        return flow {
            emit(tweetDao.getTweetsWithSenders()
                .map {
                    Tweet(
                        it.tweet.id,
                        it.tweet.content,
                        it.sender.id,
                        it.tweet.createAt
                    ).apply {
                        sender = it.sender
                    }
                }
                .applySortByCreatedAt()
            )
        }
    }

    private fun List<Tweet>.applySortByCreatedAt(): List<Tweet> {
        return this.sortedByDescending { it.createAt }
    }

    data class ValidTweet(
        val content: String,
        val sender: TweetModel.TweetSender,
        val images: List<TweetModel.TweetImage>,
        val comments: List<TweetModel.TweetComments>,
        val createAt: Long,
    )

    override suspend fun loadTweets() {
        this.api.getTweets()
            .mapNotNull {
                val validTweet = if (it.content == null || it.sender == null) null else ValidTweet(
                    it.content,
                    it.sender,
                    it.images ?: emptyList(),
                    it.comments ?: emptyList(),
                    it.date?.safeToMill() ?: System.currentTimeMillis()
                )
                validTweet
            }
            .forEachIndexed { index, it ->
                val sender = Sender(
                    id = "sender_${it.sender.nick}_${index}",
                    userName = it.sender.username,
                    nick = it.sender.nick,
                    avatar = it.sender.avatar,
                )
                val tweet = Tweet(
                    id = "tweet_${index}",
                    content = it.content,
                    senderId = sender.id,
                    createAt = it.createAt
                )

                senderDao.insertSender(sender)
                tweetDao.insertTweet(tweet)
            }
    }
}