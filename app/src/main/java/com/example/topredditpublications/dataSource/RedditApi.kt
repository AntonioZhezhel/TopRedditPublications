package com.example.topredditpublications.dataSource

import android.telecom.Call
import androidx.room.Query
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RedditApi {
    @GET("/r/all/top.json")
    fun getPosts(@Query("limit") loadSize: Int = 30,
                 @Query("after") after: String? = null,
                 @Query("before") before: String? = null): Call<RedditApiResponse>

    companion object {
        private const val BASE_URL = "https://www.reddit.com/"

        fun createService(): RedditApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RedditApi::class.java)
        }
    }
}