package com.thoughtworks.androidtrain.tweet.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object TweetNetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideTweetService(retrofit: Retrofit): TweetService = retrofit.create(TweetService::class.java)
}

const val BASE_URL: String = "https://raw.githubusercontent.com"
