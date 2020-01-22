package com.example.topredditpublications.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.topredditpublications.dataSource.RedditPost

@Dao
interface RedditPostDao {
@Insert(onConflict = OnConflictStrategy.REPLACE)
fun insert(posts: List<RedditPost>)

    @Query("SELECT * FROM RedditPost")
    fun posts(): DataSource.Factory<Int, RedditPost>
}