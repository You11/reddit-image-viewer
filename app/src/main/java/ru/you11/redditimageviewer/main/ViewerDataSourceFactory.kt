package ru.you11.redditimageviewer.main

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.paging.DataSource
import ru.you11.redditimageviewer.model.RedditPost

class ViewerDataSourceFactory(private val subreddit: String) : DataSource.Factory<String, RedditPost>() {

    override fun create(): DataSource<String, RedditPost> {
        return ViewerDataSource(subreddit)
    }
}