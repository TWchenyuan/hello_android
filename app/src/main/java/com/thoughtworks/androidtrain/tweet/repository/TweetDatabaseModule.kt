package com.thoughtworks.androidtrain.tweet.repository

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TweetDatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        TweetDatabase::class.java,
        "tweet_database"
    ).build()

    @Provides
    fun provideTweetDao(database: TweetDatabase) = database.tweetDao()

    @Provides
    fun provideSenderDao(database: TweetDatabase) = database.senderDao()
}
