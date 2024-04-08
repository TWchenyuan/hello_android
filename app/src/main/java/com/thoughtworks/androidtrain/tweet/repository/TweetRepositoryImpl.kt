package com.thoughtworks.androidtrain.tweet.repository

import com.thoughtworks.androidtrain.tweet.dao.SenderDao
import com.thoughtworks.androidtrain.tweet.dao.TweetDao
import com.thoughtworks.androidtrain.tweet.model.Sender
import com.thoughtworks.androidtrain.tweet.model.Tweet
import com.thoughtworks.androidtrain.tweet.model.asTweet
import com.thoughtworks.androidtrain.tweet.network.TweetModel
import com.thoughtworks.androidtrain.tweet.network.TweetService
import com.thoughtworks.androidtrain.util.safeToMill
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class TweetRepositoryImpl @Inject constructor(
    private val tweetDao: TweetDao,
    private val senderDao: SenderDao,
    private val api: TweetService,
) : TweetRepository {
    override fun fetchTweets(): Flow<List<Tweet>> {
        return tweetDao.getTweetsWithSenders()
            .map {
                it.map { it.asTweet() }.applySortByCreatedAt()
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

    override suspend fun loadTweets(): List<Tweet> {
        this.api.getTweets()
            .mapNotNull {
                val validTweet = if (it.content == null || it.sender == null) null
                else ValidTweet(
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
        return tweetDao.getTweetsWithSenders().map { it.map { it.asTweet() } }.first()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class TweetRepositoryModule {

    @Binds
    abstract fun bindTweetRepository(repositoryImpl: TweetRepositoryImpl): TweetRepository
}