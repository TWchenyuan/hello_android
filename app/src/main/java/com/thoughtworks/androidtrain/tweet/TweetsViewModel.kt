package com.thoughtworks.androidtrain.tweet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.thoughtworks.androidtrain.tweet.repository.TweetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TweetsViewModel @Inject constructor(
    private val repository: TweetRepository,
) : ViewModel() {
    private val _loadError = MutableLiveData(false)
    private val _loadErrorMessage = MutableLiveData<String?>()

    val tweetsLiveData = liveData {
        val tweets = repository.fetchTweets().first()
        emit(tweets)
    }

    val errorMessage: LiveData<String?>
        get() = _loadErrorMessage

    init {
        loadTweets()
    }

    fun loadTweets() {
        viewModelScope.launch {
            try {
                repository.loadTweets()
            } catch (e: Exception) {
                _loadError.postValue(true)
                _loadErrorMessage.postValue(e.message)
            }
        }
    }
}