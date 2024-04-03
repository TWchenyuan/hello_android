package com.thoughtworks.androidtrain.tweet.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(tableName = "sender")
data class Sender(
    @PrimaryKey()
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "user_name")
    val userName: String,
    @ColumnInfo(name = "nick")
    val nick: String,
    @ColumnInfo(name = "avatar")
    val avatar: String)

