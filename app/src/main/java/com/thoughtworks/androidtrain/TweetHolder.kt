package com.thoughtworks.androidtrain

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TweetHolder(itemView: View, val isFooter: Boolean) : RecyclerView.ViewHolder(itemView) {
    val nickView: TextView? = if (!isFooter) itemView.findViewById(R.id.nick_view) else null
    val contentView: TextView? =
        if (!isFooter) itemView.findViewById(R.id.content_detail_view) else null
}