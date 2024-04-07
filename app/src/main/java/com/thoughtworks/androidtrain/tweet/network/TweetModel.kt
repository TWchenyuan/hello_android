package com.thoughtworks.androidtrain.tweet.network

data class TweetModel(
    val content: String?,
    val images: List<TweetImage>?,
    val sender: TweetSender?,
    val comments: List<TweetComments>?,
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
