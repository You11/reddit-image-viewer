package ru.you11.redditimageviewer.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.github.piasy.biv.view.BigImageView
import ru.you11.redditimageviewer.R
import ru.you11.redditimageviewer.model.RedditPost

class ViewerRVAdapter(private val listener: OnImageClickListener) :
    PagedListAdapter<RedditPost, ViewerRVViewHolder>(POST_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewerRVViewHolder {
        return ViewerRVViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_image_item,
                parent,
                false
            ) as View,
            listener
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