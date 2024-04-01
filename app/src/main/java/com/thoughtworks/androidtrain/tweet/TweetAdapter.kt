package com.thoughtworks.androidtrain.tweet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.thoughtworks.androidtrain.R
import com.thoughtworks.androidtrain.tweet.model.Tweet

const val CURRENT_IN_FOOTER = 1

class TweetAdapter(private var tweets: List<Tweet>) : RecyclerView.Adapter<TweetHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetHolder {
        val res = when (viewType) {
            CURRENT_IN_FOOTER -> R.layout.tweet_footer
            else -> R.layout.tweet_item
        }
        return TweetHolder(
            LayoutInflater.from(parent.context).inflate(res, parent, false),
            viewType == CURRENT_IN_FOOTER
        )
    }

    fun rebuild() {
        this.tweets = tweets.shuffled()
    }

    override fun getItemCount(): Int = tweets.size + 1

    override fun onBindViewHolder(holder: TweetHolder, position: Int) {
        if (!holder.isFooter) {
            val tweet = tweets[position]
            holder.nickView?.text = tweet.getSender()?.nick
            holder.contentView?.text = tweet.getContent()
            tweet.getSender()?.avatar?.let {
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