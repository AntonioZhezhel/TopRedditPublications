package com.example.topredditpublications

import androidx.recyclerview.widget.DiffUtil
import com.example.topredditpublications.dataSource.RedditPost

class DiffUtilCallback : DiffUtil.ItemCallback<RedditPost>() {
    override fun areItemsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
        return oldItem.key == newItem.key
    }

    override fun areContentsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {

        return oldItem.author == newItem.author
                && oldItem.title == newItem.title
                && oldItem.num_comments == newItem.num_comments
                && oldItem.created_utc == newItem.created_utc
                && oldItem.thumbnail == newItem.thumbnail
                && oldItem.url == newItem.url
    }
}