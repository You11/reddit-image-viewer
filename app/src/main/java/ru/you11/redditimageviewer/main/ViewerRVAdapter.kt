package ru.you11.redditimageviewer.main

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.you11.redditimageviewer.R
import ru.you11.redditimageviewer.model.RedditPost

class ViewerRVAdapter(private val data: ArrayList<String>) : RecyclerView.Adapter<ViewerRVViewHolder>() {

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
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(newData: ArrayList<String>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}