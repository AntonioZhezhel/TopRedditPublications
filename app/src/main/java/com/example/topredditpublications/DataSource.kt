package com.example.topredditpublications

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.topredditpublications.dataSource.RedditApi
import com.example.topredditpublications.dataSource.RedditPost
import com.example.topredditpublications.dataSource.RedditResponse
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class DataSource: PageKeyedDataSource<String,RedditPost>() {

    private val messagesFromApi =RedditApi.createService()

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, RedditPost>
    ) {

        messagesFromApi.getPosts(loadSize = params.requestedLoadSize)
            .enqueue(object : retrofit2.Callback<RedditResponse>{
                override fun onFailure(call: Call<RedditResponse>?, t: Throwable?) {
                    Log.e("DataSource", "Failed to fetch data!")
                }

                override fun onResponse(
                    call: Call<RedditResponse>,
                    response: Response<RedditResponse>
                ) {

                    val listing = response.body()?.data
                    val redditPost= listing?.children?.map { it.data }

                }
            })
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, RedditPost>
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}