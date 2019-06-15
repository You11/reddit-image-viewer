package ru.you11.redditimageviewer.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.you11.redditimageviewer.model.RedditPost

class ViewerViewModel(private val viewerRepository: ViewerRepository = ViewerRepository()): ViewModel() {

    val urls: MutableLiveData<List<String>> = MutableLiveData()

    fun getUrls(): LiveData<List<String>> {
        GlobalScope.launch {
            val list = viewerRepository.getPosts()
            urls.postValue(getImageUrlsFromRedditPost(list))
        }

        return urls
    }

    private fun getImageUrlsFromRedditPost(posts: List<RedditPost>): ArrayList<String> {
        val urls = ArrayList<String>()
        posts.forEach { urls.add(it.title) }
        return urls
    }
}