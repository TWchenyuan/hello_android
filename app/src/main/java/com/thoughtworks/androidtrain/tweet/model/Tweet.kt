package com.thoughtworks.androidtrain.tweet.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.UUID


@Entity(
    tableName = "tweet",
    indices = []
)
data class Tweet(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo("content")
    val content: String?,
    @ColumnInfo("sender_id")
    val senderId: String,
) {
    @Ignore
    var sender: Sender? = null
}

data class TweetAndSender(
    @Embedded val tweet: Tweet,
    @Relation(
        parentColumn = "senderId",
        entityColumn = "id"
    )
    val sender: Sender,
)

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