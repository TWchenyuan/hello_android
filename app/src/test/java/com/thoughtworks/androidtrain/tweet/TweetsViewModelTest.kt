package com.thoughtworks.androidtrain.tweet

import com.thoughtworks.androidtrain.tweet.repository.TweetRepository
import org.junit.Test
import org.mockito.Mockito.mock


class TweetsViewModelTest {
    private val mockedRepository: TweetRepository = mock()
    private val tweetsViewModel: TweetsViewModel = TweetsViewModel(mockedRepository)

    @Test
    fun `should load tweets when load`()  {

    }

}