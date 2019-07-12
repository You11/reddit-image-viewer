package ru.you11.redditimageviewer.main

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.you11.redditimageviewer.R
import ru.you11.redditimageviewer.model.RedditPost

class ViewerRVAdapter : PagedListAdapter<RedditPost, ViewerRVViewHolder>(POST_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewerRVViewHolder {
        return ViewerRVViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_image_item,
                parent,
                false
            ) as View
        )
    }

    override fun onBindViewHolder(holder: ViewerRVViewHolder, position: Int) {
        holder.bind(getItem(position) ?: RedditPost("", ""))
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<RedditPost>() {
            override fun areContentsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean = oldItem == newItem

            override fun areItemsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean = oldItem.id == newItem.id
        }
    }
}