package com.thoughtworks.androidtrain.compose.tweetlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.thoughtworks.androidtrain.R
import com.thoughtworks.androidtrain.tweet.TweetUiState
import com.thoughtworks.androidtrain.tweet.TweetsViewModel

@Composable
fun TweetListScreen(viewModel: TweetsViewModel = hiltViewModel()) {
    val tweets by viewModel.tweetListUiState.collectAsState()

    TweetListScreen(
        tweets = tweets
    ) { comment -> viewModel.saveComment(comment) }
}

@Composable
fun TweetListScreen(tweets: List<TweetUiState>, onSaveComment: (comment: String) -> Unit) {
    val context = LocalContext.current
    val previewImageUrlState =
        remember {
            mutableStateOf("")
        }
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(top = 10.dp)) {
        itemsIndexed(tweets) { index, tweet ->
            TweetListItem(
                tweet,
                onSaveComment = onSaveComment,
                onClickAvatar = {
                    previewImageUrlState.value = tweet.avatar
                }
            )
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

    if (previewImageUrlState.value.isNotBlank()) {
        PreviewImage(url = previewImageUrlState.value) { previewImageUrlState.value = "" }
    }
}

@Composable
fun PreviewImage(url: String, onClosePreviewImage: () -> Unit) {
    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(Color.Black.copy(0.5f))
            .clickable { onClosePreviewImage() },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier =
            Modifier
                .background(Color.White)
                .size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = url,
                contentDescription = "preview avatar",
                placeholder = painterResource(id = R.mipmap.avatar),
                modifier =
                Modifier
                    .size(250.dp)
                    .clip(CircleShape)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TweetListScreenPreview() {
    TweetListScreen(
        emptyList()
    ) {}
}
