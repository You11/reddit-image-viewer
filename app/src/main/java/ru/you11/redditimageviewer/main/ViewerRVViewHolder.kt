package ru.you11.redditimageviewer.main

import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.piasy.biv.view.BigImageView
import com.github.piasy.biv.view.GlideImageViewFactory
import ru.you11.redditimageviewer.R
import ru.you11.redditimageviewer.model.RedditPost

class ViewerRVViewHolder(private val layout: View,
                         private val listener: OnImageClickListener) : RecyclerView.ViewHolder(layout) {

    fun bind(post: RedditPost) {
        val imageView = layout.findViewById<ImageView>(R.id.images_rv_image)
        Glide.with(layout).load(post.url).placeholder(R.drawable.default_placeholder).into(imageView)

        imageView.setOnClickListener {
            listener.onImageClick(post.url)
        }
    }
}

interface OnImageClickListener {

    fun onImageClick(url: String)
}