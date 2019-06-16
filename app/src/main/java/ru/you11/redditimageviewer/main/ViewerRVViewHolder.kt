package ru.you11.redditimageviewer.main

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.module.AppGlideModule
import ru.you11.redditimageviewer.R

class ViewerRVViewHolder(private val layout: View) : RecyclerView.ViewHolder(layout) {

    fun bind(url: String) {
        val imageView = layout.findViewById<ImageView>(R.id.images_rv_image)
        Glide.with(layout).load(url).placeholder(R.drawable.default_placeholder).into(imageView)
    }
}