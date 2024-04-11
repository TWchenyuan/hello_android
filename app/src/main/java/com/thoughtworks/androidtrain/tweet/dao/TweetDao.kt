package com.thoughtworks.androidtrain.tweet.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.thoughtworks.androidtrain.tweet.model.Tweet
import com.thoughtworks.androidtrain.tweet.model.TweetAndSender
import kotlinx.coroutines.flow.Flow

@Dao
interface TweetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTweet(tweet: Tweet)

    @Transaction
    @Query("SELECT * FROM tweet")
    fun getTweetsWithSenders(): Flow<List<TweetAndSender>>
}
