package com.example.topredditpublications.dataSource

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface RedditApi {
    @GET("/r/all/top.json")
    fun getPosts(@retrofit2.http.Query("limit") loadSize: Int = 30,
                 @retrofit2.http.Query("after") after: String? = null,
                 @retrofit2.http.Query("before") before: String? = null): retrofit2.Call<RedditResponse>

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