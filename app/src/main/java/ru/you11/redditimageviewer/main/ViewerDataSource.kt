package ru.you11.redditimageviewer.main

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.paging.ItemKeyedDataSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.you11.redditimageviewer.api.ApiPost
import ru.you11.redditimageviewer.api.ApiService
import ru.you11.redditimageviewer.api.IApiMethods
import ru.you11.redditimageviewer.api.RetrofitFactory
import ru.you11.redditimageviewer.model.RedditPost

class ViewerDataSource(private var subreddit: String) : ItemKeyedDataSource<String, RedditPost>() {

    private val retrofit = RetrofitFactory().create()
    private val apiService = ApiService(retrofit.create(IApiMethods::class.java))

    override fun getKey(item: RedditPost): String = item.id

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<RedditPost>) {
        val data = ApiPost.convertToPostList(apiService.getInitialPosts(subreddit))
        callback.onResult(data)
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<RedditPost>) {
        val data = ApiPost.convertToPostList(apiService.getAfterPosts(subreddit, params.key))
        callback.onResult(data)
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<RedditPost>) {

    }

    fun changeSubreddit(newSubreddit: String) {
        subreddit = newSubreddit
    }
}