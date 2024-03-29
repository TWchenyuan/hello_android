package com.thoughtworks.androidtrain

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TweetHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nickView: TextView = itemView.findViewById(R.id.nick_view)
    val contentView: TextView = itemView.findViewById(R.id.content_detail_view)
}