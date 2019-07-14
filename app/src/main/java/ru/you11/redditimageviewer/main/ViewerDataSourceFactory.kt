package ru.you11.redditimageviewer.main

import androidx.paging.DataSource
import ru.you11.redditimageviewer.model.RedditPost

class ViewerDataSourceFactory(private val subreddit: String,
                              private val viewerViewModel: ViewerViewModel) : DataSource.Factory<String, RedditPost>() {

    override fun create(): DataSource<String, RedditPost> {
        return ViewerDataSource(subreddit, viewerViewModel)
    }
}