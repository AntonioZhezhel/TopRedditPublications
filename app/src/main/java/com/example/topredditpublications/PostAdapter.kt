package com.example.topredditpublications


import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.example.topredditpublications.dataSource.RedditPost
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_row.view.*
import java.util.*

class PostAdapter() : PagedListAdapter<RedditPost,PostViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_row,parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        val item = getItem(position)
        val resourse = holder.itemView.context.resources
        val commentCountString = resourse.getString(R.string.comments,item?.num_comments!!)
        val timestamp = item.created_utc * 1000L
        val date = Date(timestamp)
        val dateStr = DateUtils.getRelativeTimeSpanString(date.time,
            Calendar.getInstance().timeInMillis,DateUtils.MINUTE_IN_MILLIS)

        holder.itemView.tvAuthor.text = "Posted by: U/" + item.author
        holder.itemView.tvCommentQuantity.text = commentCountString
        holder.itemView.tvTitle.text = item.title
        holder.itemView.tvPublicationTime.text = dateStr
        Picasso.get().load(item.thumbnail).into(holder.itemView.ibThumbnail)
    }
}