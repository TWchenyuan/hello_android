package com.thoughtworks.androidtrain.tweet.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "image")
data class Image(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "url")
    var url: String
)
