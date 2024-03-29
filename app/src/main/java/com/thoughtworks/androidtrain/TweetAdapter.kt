package com.thoughtworks.androidtrain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TweetAdapter(private val tweets: List<Pair<String, String>>) : RecyclerView.Adapter<TweetHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetHolder {
        return TweetHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.tweet_item, parent, false)
        )
    }

    override fun getItemCount(): Int = tweets.size

    override fun onBindViewHolder(holder: TweetHolder, position: Int) {
        val tweet = tweets[position]
        holder.nickView.text = tweet.first
        holder.contentView.text = tweet.second
    }
}