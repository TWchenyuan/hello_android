package com.thoughtworks.androidtrain.tweet.dao

import androidx.room.Dao

import androidx.room.Insert

import androidx.room.OnConflictStrategy
import com.thoughtworks.androidtrain.tweet.model.Sender

@Dao
interface SenderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSenders(vararg senders: Sender)

    @Insert()
    fun insertSender(sender: Sender)
}


