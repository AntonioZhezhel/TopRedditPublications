package com.example.topredditpublications

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topredditpublications.dataSource.RedditPost
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Bundle as Bundle1


open class MainActivity : AppCompatActivity() {
    private val adapter = PostAdapter()

    override fun onCreate(savedInstanceState: Bundle1?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initList()
    }


    private fun initList() {

        rvListPost.layoutManager = LinearLayoutManager(this)
        rvListPost.adapter = adapter

        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .build()

        val liveData = initPagedListBuilder(config).build()
        liveData.observe(this, Observer <PagedList<RedditPost>>{pagedList ->
            adapter.submitList(pagedList)
        })

}

    private fun initPagedListBuilder(config: PagedList.Config?): LivePagedListBuilder<String, RedditPost> {

        val dataSourceFactory = object : androidx.paging.DataSource.Factory<String, RedditPost>() {
            override fun create(): androidx.paging.DataSource<String, RedditPost> {

                return DataSource()
            }
        }

        return LivePagedListBuilder<String,RedditPost>(dataSourceFactory,config!!)
    }





    }

