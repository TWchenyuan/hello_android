package com.thoughtworks.androidtrain.tweet.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thoughtworks.androidtrain.tweet.dao.SenderDao
import com.thoughtworks.androidtrain.tweet.dao.TweetDao
import com.thoughtworks.androidtrain.tweet.model.Comment
import com.thoughtworks.androidtrain.tweet.model.Image
import com.thoughtworks.androidtrain.tweet.model.Sender
import com.thoughtworks.androidtrain.tweet.model.Tweet

@Database(entities = [Tweet::class, Sender::class, Image::class, Comment::class], version = 1)
abstract class TweetDatabase : RoomDatabase() {
    abstract fun tweetDao(): TweetDao
    abstract fun senderDao(): SenderDao

    companion object {
        fun dbInstance(context: Context): TweetDatabase {
            return Room.databaseBuilder(
                context,
                TweetDatabase::class.java,
                "tweet-database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }

    }
}