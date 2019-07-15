package ru.you11.redditimageviewer.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.you11.redditimageviewer.model.RedditPost
import ru.you11.redditimageviewer.other.ImageDetector

class ViewerViewModel: ViewModel() {

    val currentSubreddit: MutableLiveData<String> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()
    val loadingStatus: MutableLiveData<LoadingStatus> = MutableLiveData(LoadingStatus.LOADING)

    fun updateSubreddit(subreddit: String) {
        currentSubreddit.postValue(subreddit)
    }

    fun updateError(error: String) {
        this.error.postValue(error)
    }

    fun changeLoadingStatus(status: LoadingStatus) {
        loadingStatus.postValue(status)
    }
}