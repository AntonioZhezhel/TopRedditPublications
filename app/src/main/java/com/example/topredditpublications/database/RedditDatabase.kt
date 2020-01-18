package com.example.topredditpublications.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.topredditpublications.dataSource.RedditPost

@Database(
    entities = [RedditPost::class],
    version = 1,
    exportSchema = false
)
abstract class RedditDatabase: RoomDatabase() {
    companion object {
        fun create(context: Context): RedditDatabase {
            val databaseBuilder = Room.databaseBuilder(context, RedditDatabase::class.java, "redditclone.db")
            return databaseBuilder.build()
        }
    }

    abstract fun postDao(): RedditPostDao

}