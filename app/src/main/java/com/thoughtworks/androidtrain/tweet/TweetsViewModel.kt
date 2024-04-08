package com.thoughtworks.androidtrain.tweet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.androidtrain.tweet.model.Tweet
import com.thoughtworks.androidtrain.tweet.repository.TweetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TweetsViewModel @Inject constructor(
    private val repository: TweetRepository,
) : ViewModel() {
    private val _loadError = MutableLiveData(false)
    private val _loadErrorMessage = MutableLiveData<String?>()
    private val _tweetLiveData = MutableLiveData<List<Tweet>>()

    val tweetsLiveData = _tweetLiveData

    val errorMessage: LiveData<String?>
        get() = _loadErrorMessage

    init {
        loadTweets()
    }

    fun loadTweets() {
        viewModelScope.launch {
            try {
                val newData = repository.loadTweets()
                _tweetLiveData.value = newData
            } catch (e: Exception) {
                _loadError.value = true
                _loadErrorMessage.value = e.message
            }
        }
    }
}