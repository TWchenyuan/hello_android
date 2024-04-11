package com.thoughtworks.androidtrain.compose.tweetlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.thoughtworks.androidtrain.R
import com.thoughtworks.androidtrain.tweet.TweetUiState

@Composable
fun TweetListItem(
    tweet: TweetUiState,
    onSaveComment: (comment: String) -> Unit = {},
    onClickAvatar: (url: String) -> Unit = {},
) {
    val showCommentEditorState = remember { mutableStateOf(false) }
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        AsyncImage(
            model = tweet.avatar,
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.mipmap.avatar),
            modifier =
            Modifier
                .size(100.dp)
                .clickable {
                    onClickAvatar(tweet.avatar)
                }
        )
        Column(
            modifier =
            Modifier
                .fillMaxHeight()
                .wrapContentWidth()
                .padding(start = 10.dp)
        ) {
            Text(
                text = tweet.nick,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = tweet.content,
                style = MaterialTheme.typography.bodyMedium,
                modifier =
                Modifier.clickable {
                    showCommentEditorState.value = true
                }
            )
            if (showCommentEditorState.value) {
                EditComment(
                    onSave = { comment ->
                        onSaveComment(comment)
                        showCommentEditorState.value = false
                    },
                    onCancel = {
                        showCommentEditorState.value = false
                    }
                )
            }
        }
    }
}

@Composable
fun EditComment(onSave: (comment: String) -> Unit, onCancel: () -> Unit) {
    val commentState = remember {
        mutableStateOf("")
    }
    BasicTextField(
        value = commentState.value,
        onValueChange = {
            commentState.value = it
        },
        modifier =
        Modifier
            .height(35.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Black
            ),
        decorationBox = { textFiled ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier =
                    Modifier
                        .weight(1f)
                        .padding(start = 2.dp)
                ) {
                    textFiled()
                }
                TextButton(onClick = { onSave(commentState.value) }) {
                    Text(text = "save")
                }

                TextButton(onClick = onCancel) {
                    Text(text = "cancel")
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun TweetListItemPreview() {
    TweetListItem(
        TweetUiState("id", "content", "nick_name", "avatar_url", null),
        showCommentEditorState =
        remember {
            mutableStateOf(false)
        },
    )
}
