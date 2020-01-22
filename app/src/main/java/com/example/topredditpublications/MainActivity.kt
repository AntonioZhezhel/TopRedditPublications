package com.example.topredditpublications

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topredditpublications.dataSource.RedditPost
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import android.os.Bundle as Bundle1


class MainActivity : AppCompatActivity() {




     private val adapter = PostAdapter {
         val mDialog = LayoutInflater.from(this).inflate(R.layout.custom_dialog,null)
         val dialog = AlertDialog.Builder(this).setView(mDialog)
         val alert = dialog.show()

         alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
         Picasso.get().load(it.url).into(mDialog.ivImageURL)
         mDialog.bnSave.setOnClickListener {
             Toast.makeText(this, "save" , Toast.LENGTH_SHORT).show()
             alert.dismiss()
         }

         mDialog.bnCancel.setOnClickListener {
             alert.dismiss()
         }


     }

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

//    fun bt(view: View) {
//
//        val mDialog = LayoutInflater.from(this).inflate(R.layout.custom_dialog,null)
//        val dialog = AlertDialog.Builder(this).setView(mDialog)
//        val a = dialog.show()
//        a.tV.text = "dgdfg"
//
//        mDialog.buttonOk.setOnClickListener {
//
//            Toast.makeText(this, "ok" , Toast.LENGTH_SHORT).show()
//
//
//            a.dismiss()
//
//        }
//    }


}

