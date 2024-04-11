package com.thoughtworks.androidtrain.tweet

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.androidtrain.tweet.model.Tweet
import com.thoughtworks.androidtrain.tweet.repository.TweetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TweetUiState(
    val id: String,
    val content: String,
    val nick: String,
    val avatar: String,
    val comment: String?,
)

@HiltViewModel
class TweetsViewModel @Inject constructor(
    private val repository: TweetRepository,
) : ViewModel() {
    private val _loadError = MutableLiveData(false)
    private val _loadErrorMessage = MutableLiveData<String?>()
    private val _tweetLiveData = MutableLiveData<List<Tweet>>()
    private val _tweetListUiState = MutableStateFlow(emptyList<TweetUiState>())

    val tweetsLiveData = _tweetLiveData
    val tweetListUiState: StateFlow<List<TweetUiState>> = _tweetListUiState.asStateFlow()

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
                _tweetListUiState.value = newData.mapNotNull {
                    if (it.sender == null || it.content == null) null
                    else TweetUiState(
                        id = it.id,
                        content = it.content,
                        nick = it.sender!!.nick,
                        avatar = it.sender!!.avatar,
                        comment = null
                    )
                }
            } catch (e: Exception) {
                _loadError.value = true
                _loadErrorMessage.value = e.message
            }
        }
    }

    fun saveComment(comment: String) {
        //TODO save to database
    }
}