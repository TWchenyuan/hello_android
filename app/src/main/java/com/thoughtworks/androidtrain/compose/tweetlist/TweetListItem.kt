package com.thoughtworks.androidtrain.compose.tweetlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.thoughtworks.androidtrain.R
import com.thoughtworks.androidtrain.tweet.model.Sender
import com.thoughtworks.androidtrain.tweet.model.Tweet
import java.time.Instant

@Composable
fun TweetListItem(
    tweet: Tweet,
    previewImageState: MutableState<Boolean>,
    previewImageUrlState: MutableState<String>,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        AsyncImage(
            model = tweet.sender?.avatar ?: "",
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.mipmap.avatar),
            modifier = Modifier
                .size(100.dp)
                .clickable {
                    previewImageState.value = true
                    previewImageUrlState.value = tweet.sender?.avatar ?: ""
                },
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth()
                .padding(start = 10.dp)
        ) {
            Text(
                text = tweet.sender?.nick ?: "", style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = tweet.content ?: "", style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun TweetListItemPreview() {
    TweetListItem(
        tweet = Tweet(
            id = "tweet_2", content = """
           content
        """.trimIndent(), senderId = "sender_1", createAt = Instant.now().toEpochMilli()
        ).apply {
            sender = Sender(
                "sender_1",
                "john",
                "john",
                "https://c-ssl.dtstatic.com/uploads/blog/202104/02/20210402200403_1e37e.thumb.1000_0.jpeg"
            )
        },
        previewImageState = remember { mutableStateOf(false) },
        previewImageUrlState = remember {
            mutableStateOf("")
        }
    )
}