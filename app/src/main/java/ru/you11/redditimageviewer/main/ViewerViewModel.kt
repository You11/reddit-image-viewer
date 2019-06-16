package ru.you11.redditimageviewer.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.you11.redditimageviewer.model.RedditPost
import ru.you11.redditimageviewer.other.ImageDetector

class ViewerViewModel(private val viewerRepository: ViewerRepository = ViewerRepository()): ViewModel() {

    val urls: MutableLiveData<List<String>> = MutableLiveData()

    fun getUrls(subreddit: String): LiveData<List<String>> {
        GlobalScope.launch {
            val list = viewerRepository.getPosts(subreddit)
            urls.postValue(getImageUrlsFromRedditPost(list))
        }

        return urls
    }

    private fun getImageUrlsFromRedditPost(posts: List<RedditPost>): ArrayList<String> {
        val urls = ArrayList<String>()
        val imageDetector = ImageDetector()
        posts.forEach {
            if (imageDetector.isUrlContainsImageExtensions(it.url)) urls.add(it.url)
        }
        return urls
    }
}