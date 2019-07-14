package ru.you11.redditimageviewer.main

import androidx.paging.ItemKeyedDataSource
import ru.you11.redditimageviewer.api.ApiPost
import ru.you11.redditimageviewer.api.ApiService
import ru.you11.redditimageviewer.api.IApiMethods
import ru.you11.redditimageviewer.api.RetrofitFactory
import ru.you11.redditimageviewer.model.RedditPost
import ru.you11.redditimageviewer.other.ImageDetector

class ViewerDataSource(private val subreddit: String,
                       private val viewModel: ViewerViewModel) : ItemKeyedDataSource<String, RedditPost>() {

    private val retrofit = RetrofitFactory().create()
    private val apiService = ApiService(retrofit.create(IApiMethods::class.java))

    override fun getKey(item: RedditPost): String = item.id

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<RedditPost>) {

        val result = apiService.getInitialPosts(subreddit)
        if (result.isSuccess) {
            val data = ApiPost.convertToPostList(result.data)
            callback.onResult(getImagePostsFromAllPosts(data))
        } else {
            viewModel.updateError(result.error)
            callback.onResult(ArrayList())
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<RedditPost>) {

        val result = apiService.getAfterPosts(subreddit, params.key)
        if (result.isSuccess) {
            val data = ApiPost.convertToPostList(result.data)
            callback.onResult(getImagePostsFromAllPosts(data))
        } else {
            viewModel.updateError(result.error)
            callback.onResult(ArrayList())
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<RedditPost>) {

    }

    private fun getImagePostsFromAllPosts(posts: List<RedditPost>): ArrayList<RedditPost> {
        val imagePosts = ArrayList<RedditPost>()
        val imageDetector = ImageDetector()
        posts.forEach {
            if (imageDetector.isUrlContainsImageExtensions(it.url)) imagePosts.add(it)
        }
        return imagePosts
    }
}