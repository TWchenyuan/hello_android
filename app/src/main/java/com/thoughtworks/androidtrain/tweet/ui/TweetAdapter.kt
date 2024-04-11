package com.thoughtworks.androidtrain.tweet.ui

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.thoughtworks.androidtrain.R
import com.thoughtworks.androidtrain.tweet.model.Tweet
import com.thoughtworks.androidtrain.util.formatDateString

const val CURRENT_IN_FOOTER = 1

class TweetAdapter(
    private var tweets: MutableList<Tweet> = mutableListOf()
) : RecyclerView.Adapter<TweetHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetHolder {
        val res =
            when (viewType) {
                CURRENT_IN_FOOTER -> R.layout.tweet_footer
                else -> R.layout.tweet_item
            }
        return TweetHolder(
            LayoutInflater.from(parent.context).inflate(res, parent, false),
            viewType == CURRENT_IN_FOOTER
        )
    }

    fun updateTweets(newTweets: List<Tweet>) {
        this.tweets.clear()
        this.tweets.addAll(newTweets)
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = tweets.size + 1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TweetHolder, position: Int) {
        if (!holder.isFooter) {
            val tweet = tweets[position]
            holder.nickView?.text = tweet.sender?.nick
            holder.contentView?.text = tweet.content
            holder.createdAtView?.text = tweet.createAt.formatDateString()
            tweet.sender?.avatar?.let {
                holder.avatarView?.load(it) {
                    crossfade(true)
                    placeholder(R.mipmap.avatar)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == itemCount - 1) return CURRENT_IN_FOOTER
        return super.getItemViewType(position)
    }
}
