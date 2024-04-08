package com.thoughtworks.androidtrain.tweet.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity(
    tableName = "tweet",
    indices = []
)
data class Tweet(
    @PrimaryKey
    val id: String,
    @ColumnInfo("content")
    val content: String?,
    @ColumnInfo("sender_id")
    val senderId: String,
    @ColumnInfo("create_at")
    val createAt: Long,
) {
    @Ignore
    var sender: Sender? = null
}

data class TweetAndSender(
    @Embedded val tweet: Tweet,
    @Relation(
        parentColumn = "sender_id",
        entityColumn = "id"
    )
    val sender: Sender,
)

fun TweetAndSender.asTweet(): Tweet {
    return Tweet(
        this.tweet.id,
        this.tweet.content,
        this.sender.id,
        this.tweet.createAt
    ).apply {
        sender = this@asTweet.sender
    }
}

data class TweetAndImages(
    @Embedded val tweet: Tweet,
    @Relation(
        parentColumn = "tweet_id",
        entityColumn = "image_tweet_id"
    )
    val images: List<Image>,
)

data class TweetAndComments(
    @Embedded val tweet: Tweet,
    @Relation(
        parentColumn = "tweet_id",
        entityColumn = "comment_tweet_id"
    )
    val comments: List<Comment>,
)