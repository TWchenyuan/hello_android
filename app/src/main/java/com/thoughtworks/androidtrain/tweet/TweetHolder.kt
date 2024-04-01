package com.thoughtworks.androidtrain.tweet

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.androidtrain.R

class TweetHolder(itemView: View, val isFooter: Boolean) : RecyclerView.ViewHolder(itemView) {
    val nickView: TextView? = if (!isFooter) itemView.findViewById(R.id.nick_view) else null
    val contentView: TextView? =
        if (!isFooter) itemView.findViewById(R.id.content_detail_view) else null
    val avatarView: ImageView? = if (!isFooter) itemView.findViewById(R.id.avatar_view) else null
}