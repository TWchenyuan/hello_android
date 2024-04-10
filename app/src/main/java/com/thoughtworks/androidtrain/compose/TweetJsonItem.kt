package com.thoughtworks.androidtrain.compose

data class TweetJsonItem(
    val content: String?,
    val images: List<TweetImage>?,
    val sender: TweetSender?,
    val comments: List<TweetComments>?,
    val date: String?
) {

    data class TweetImage(val url: String)
    data class TweetSender(
        val username: String,
        val nick: String,
        val avatar: String,
    )

    data class TweetComments(
        val content: String,
        val sender: TweetSender,
    )
}
