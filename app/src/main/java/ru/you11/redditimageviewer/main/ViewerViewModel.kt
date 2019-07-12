package ru.you11.redditimageviewer.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.you11.redditimageviewer.model.RedditPost
import ru.you11.redditimageviewer.other.ImageDetector

class ViewerViewModel(private val viewerRepository: ViewerRepository = ViewerRepository()): ViewModel() {

    val currentSubreddit: MutableLiveData<String> = MutableLiveData()

    fun getInitialUrls(subreddit: String): LiveData<List<RedditPost>> {
        val images: MutableLiveData<List<RedditPost>> = MutableLiveData()
        GlobalScope.launch {
            val list = viewerRepository.getPosts(subreddit)
            images.postValue(getImagePostsFromAllPosts(list))
        }

        return images
    }

    fun getAdditionalUrls(subreddit: String, afterId: String): LiveData<List<RedditPost>> {
        val images: MutableLiveData<List<RedditPost>> = MutableLiveData()
        GlobalScope.launch {
            val list = viewerRepository.getAfterPosts(subreddit, afterId)
            images.postValue(getImagePostsFromAllPosts(list))
        }

        return images
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