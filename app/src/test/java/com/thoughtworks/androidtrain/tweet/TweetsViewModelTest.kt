package com.thoughtworks.androidtrain.tweet

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.*
import com.thoughtworks.androidtrain.MainDispatcherRule
import com.thoughtworks.androidtrain.tweet.model.Sender
import com.thoughtworks.androidtrain.tweet.model.Tweet
import com.thoughtworks.androidtrain.tweet.repository.TweetRepository
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.Instant


class TweetsViewModelTest {

    private val mockedRepository: TweetRepository = mock()
    private lateinit var tweetsViewModel: TweetsViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `should load empty tweets when load`() = runTest {
        `when`(mockedRepository.loadTweets()).thenReturn(emptyList())

        tweetsViewModel = TweetsViewModel(mockedRepository)

        assertThat(tweetsViewModel.errorMessage.value).isNull()
        assertThat(tweetsViewModel.tweetsLiveData.value).isNotNull()
        assertThat(tweetsViewModel.tweetsLiveData.value).isEmpty()
    }

    @Test
    fun `should load 1 tweets when load`() = runTest {
        val tweetDate = Instant.now().toEpochMilli()
        `when`(mockedRepository.loadTweets()).thenReturn(
            listOf(
                Tweet(
                    id = "tweet_1",
                    content = "first content",
                    createAt = tweetDate,
                    senderId = "sender_id"
                ).apply {
                    sender = Sender("sender_id", "john", "john", "avatar")
                }
            )
        )

        tweetsViewModel = TweetsViewModel(mockedRepository)

        assertThat(tweetsViewModel.errorMessage.value).isNull()
        assertThat(tweetsViewModel.tweetsLiveData.value).hasSize(1)
        val tweet = tweetsViewModel.tweetsLiveData.value?.first()
        tweet.apply {
            assertThat(this).isNotNull()
            assertThat(this?.id).isEqualTo("tweet_1")
            assertThat(this?.content).isEqualTo("first content")
            assertThat(this?.createAt).isEqualTo(tweetDate)
            assertThat(this?.sender?.nick).isEqualTo("john")
        }
    }

    @Test
    fun `should find error when loadTweet failed`() = runTest {
        `when`(mockedRepository.loadTweets()).thenThrow(RuntimeException("test error message"))

        tweetsViewModel = TweetsViewModel(mockedRepository)

        assertThat(tweetsViewModel.errorMessage.value).isNotNull()
        assertThat(tweetsViewModel.errorMessage.value).contains("test error message")
    }

}