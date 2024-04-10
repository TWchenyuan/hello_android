package com.thoughtworks.androidtrain.compose.tweetlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thoughtworks.androidtrain.tweet.model.Sender
import com.thoughtworks.androidtrain.tweet.model.Tweet
import java.time.Instant

@Composable
fun TweetListItem(tweet: Tweet) {
    Row(modifier = Modifier.fillMaxSize()) {
        Column() {
            Text(text = tweet.sender?.nick ?: "")
            Text(text = tweet.content ?: "")

        }
    }
}

@Composable
fun CoilImage(url: String) {
//    val painter = rememberImagePainter()
//    Image(painter = , contentDescription = )
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun TweetListItemPreview() {
    TweetListItem(tweet = Tweet(
        id = "tweet_2",
        content = "content",
        senderId = "sender_1",
        createAt = Instant.now().toEpochMilli()
    ).apply {
        sender = Sender("sender_1", "john", "john", "avatar")
    })
}