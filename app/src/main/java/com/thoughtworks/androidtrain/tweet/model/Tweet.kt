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
    foreignKeys = [
        ForeignKey(
            entity = Sender::class,
            parentColumns = ["id"],
            childColumns = ["senderId"],
        ),
    ],
    indices = []
)
data class Tweet(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo("content")
    val content: String?,
    val senderId: String,
) {
    @Ignore
    var sender: Sender? = null

    @Ignore
    var error: String? = null

    @com.google.gson.annotations.SerializedName("unknown error")
    @Ignore
    var unknownError: String? = null

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