package com.thoughtworks.androidtrain.repository

import com.google.common.truth.Truth.assertThat
import com.thoughtworks.androidtrain.tweet.dao.SenderDao
import com.thoughtworks.androidtrain.tweet.dao.TweetDao
import com.thoughtworks.androidtrain.tweet.model.Sender
import com.thoughtworks.androidtrain.tweet.model.Tweet
import com.thoughtworks.androidtrain.tweet.model.TweetAndSender
import com.thoughtworks.androidtrain.tweet.network.TweetService
import com.thoughtworks.androidtrain.tweet.repository.TweetRepositoryImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.Instant

class TweetRepositoryImplTest {
    private val tweetDao: TweetDao = mock()
    private val senderDao: SenderDao = mock()
    private val api: TweetService = mock()
    private val repository = TweetRepositoryImpl(tweetDao, senderDao, api)

    @Test
    fun `should sort by create_at when load tweets`() = runTest {
        val now = Instant.now()
        val olderDate = now.minusSeconds(10).toEpochMilli()
        val newerDate = now.minusSeconds(1).toEpochMilli()
        val sender_1 = Sender("sender_id", "john", "john", "avatar")

        `when`(tweetDao.getTweetsWithSenders()).thenReturn(
            flowOf(
                listOf(
                    TweetAndSender(
                        tweet = Tweet(
                            id = "tweet_1",
                            content = "first content",
                            createAt = olderDate,
                            senderId = "sender_id"
                        ),
                        sender_1
                    ),
                    TweetAndSender(
                        tweet = Tweet(
                            id = "tweet_2",
                            content = "second content",
                            createAt = newerDate,
                            senderId = "sender_id"
                        ),
                        sender_1
                    )
                )
            )
        )
        val tweets = repository.fetchTweets().first()


        assertThat(tweets).isNotNull()
        assertThat(tweets).hasSize(2)
        assertThat(tweets[0].id).isEqualTo("tweet_2")
        assertThat(tweets[1].id).isEqualTo("tweet_1")
    }
}