package com.thoughtworks.androidtrain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.androidtrain.model.Tweet

class TweetAdapter(private val tweets: List<Tweet>) : RecyclerView.Adapter<TweetHolder>() {
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
        holder.nickView.text = tweet.getSender()?.nick
        holder.contentView.text = tweet.getContent()
    }
}