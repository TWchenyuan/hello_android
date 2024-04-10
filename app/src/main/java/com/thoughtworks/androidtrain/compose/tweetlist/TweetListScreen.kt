package com.thoughtworks.androidtrain.compose.tweetlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.thoughtworks.androidtrain.tweet.model.Sender
import com.thoughtworks.androidtrain.tweet.model.Tweet
import java.time.Instant

@Composable
fun TweetListScreen() {
    TweetListScreen(tweets = emptyList())
}

@Composable
fun TweetListScreen(tweets: List<Tweet>) {
    LazyColumn(Modifier.fillMaxSize()) {
        itemsIndexed(tweets) { index, tweet ->
            TweetListItem(tweet)
            if (index == tweets.size - 1) {
                Text(
                    text = "到底了",
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
    TweetListScreen(listOf(Tweet(
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
    }))
}