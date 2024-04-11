package com.thoughtworks.androidtrain.tweet.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(
    tableName = "comment",
    foreignKeys = [
        ForeignKey(
            entity = Sender::class,
            parentColumns = ["id"],
            childColumns = ["id"]
        )
    ],
    indices = []
)
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "content")
    val content: String
) {
    @Ignore
    val sender: Sender? = null
}
