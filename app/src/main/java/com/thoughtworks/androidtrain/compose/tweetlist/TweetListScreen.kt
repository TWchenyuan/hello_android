package com.thoughtworks.androidtrain.compose.tweetlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thoughtworks.androidtrain.tweet.TweetsViewModel
import com.thoughtworks.androidtrain.tweet.model.Sender
import com.thoughtworks.androidtrain.tweet.model.Tweet
import java.time.Instant
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.thoughtworks.androidtrain.R

@Composable
fun TweetListScreen(viewModel: TweetsViewModel = hiltViewModel()) {
    val tweets by viewModel.tweetsLiveData.observeAsState(initial = emptyList())
    TweetListScreen(tweets)
}

@Composable
fun TweetListScreen(tweets: List<Tweet>) {
    val context = LocalContext.current
    LazyColumn(Modifier.fillMaxSize()) {
        itemsIndexed(tweets) { index, tweet ->
            TweetListItem(tweet)
            Spacer(modifier = Modifier.height(8.dp))
            if (index == tweets.size - 1) {
                Text(
                    text = context.getString(R.string.already_bottom),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TweetListScreenPreview() {
    TweetListScreen(
        listOf(Tweet(
            id = "tweet_1",
            content = "content",
            senderId = "sender_1",
            createAt = Instant.now().toEpochMilli()

        ).apply {
            sender = Sender("sender_1", "john", "john", "avatar")
        }, Tweet(
            id = "tweet_2",
            content = "content",
            senderId = "sender_1",
            createAt = Instant.now().toEpochMilli()
        ).apply {
            sender = Sender("sender_1", "john", "john", "avatar")
        })
    )
}