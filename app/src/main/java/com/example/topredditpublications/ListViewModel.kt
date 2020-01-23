package com.example.topredditpublications

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.topredditpublications.dataSource.RedditApi
import com.example.topredditpublications.dataSource.RedditPost
import io.reactivex.disposables.CompositeDisposable

class ListViewModel : ViewModel() {
 private val messagesFromApi = RedditApi.createService()
 var postList:LiveData<PagedList<RedditPost>>
 private val compositeDisposable = CompositeDisposable()
 private val pageSize = 30
 private val postDataSourceFactory: PostDataSourceFactory


 init {
  postDataSourceFactory = PostDataSourceFactory(compositeDisposable,messagesFromApi)
  val config = PagedList.Config.Builder()
   .setPageSize(pageSize)
   .setInitialLoadSizeHint(pageSize * 2)
   .setEnablePlaceholders(false)
   .build()
  postList = LivePagedListBuilder(postDataSourceFactory,config).build()
 }

 override fun onCleared() {
  super.onCleared()
  compositeDisposable.dispose()
 }

}