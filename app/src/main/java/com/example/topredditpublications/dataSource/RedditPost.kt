package com.example.topredditpublications.dataSource

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class RedditPost (
    @SerializedName("name")
    val key: String,
    @SerializedName("title")
    @PrimaryKey
    val title: String,
    @SerializedName("created_utc")
    val created_utc: Long,
    @SerializedName("author")
    val author: String,
    @SerializedName("num_comments")
    val num_comments: Int,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("url")
    val url: String

)
