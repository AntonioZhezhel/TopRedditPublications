package com.example.topredditpublications

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.topredditpublications.dataSource.RedditApi
import com.example.topredditpublications.dataSource.RedditPost
import com.example.topredditpublications.dataSource.RedditResponse
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Response
class DataSource(
    private val messagesFromApi: RedditApi,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<String,RedditPost>() {


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
                    callback.onResult(redditPost ?: listOf(),listing?.before,listing?.after)
                }
            })
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {

        messagesFromApi.getPosts(loadSize = params.requestedLoadSize,after = params.key)
            .enqueue(object : retrofit2.Callback<RedditResponse>{

                override fun onFailure(call: Call<RedditResponse>?, t: Throwable?) {
                    Log.e("DataSource", "Failed to fetch data!")
                }

                override fun onResponse(
                    call: Call<RedditResponse>,
                    response: Response<RedditResponse>
                ) {
                    val listing = response.body()?.data
                    val items = listing?.children?.map { it.data }
                    callback.onResult(items?: listOf(),listing?.after)

                }
            })

    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, RedditPost>
    ) {

        messagesFromApi.getPosts(loadSize = params.requestedLoadSize,before = params.key)
            .enqueue(object : retrofit2.Callback<RedditResponse>{
                override fun onFailure(call: Call<RedditResponse>, t: Throwable) {
                    Log.e("DataSource", "Failed to fetch data!")
                }

                override fun onResponse(
                    call: Call<RedditResponse>,
                    response: Response<RedditResponse>
                ) {
                    val listing = response.body()?.data
                    val items = listing?.children?.map { it.data }
                    callback.onResult(items?: listOf(),listing?.before)                }

            })

    }

}