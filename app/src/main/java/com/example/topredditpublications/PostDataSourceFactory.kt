package com.example.topredditpublications

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.topredditpublications.dataSource.RedditApi
import com.example.topredditpublications.dataSource.RedditPost
import io.reactivex.disposables.CompositeDisposable

class PostDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val messagesFromApi: RedditApi
    ): DataSource.Factory<String, RedditPost>() {
     val postDataSourceLiveData = MutableLiveData<com.example.topredditpublications.DataSource>()

    override fun create(): DataSource<String, RedditPost>? {
        val newsDataSource = com.example.topredditpublications.DataSource(messagesFromApi, compositeDisposable)
        postDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }


}