package ru.you11.redditimageviewer.main

import ru.you11.redditimageviewer.api.ApiPost
import ru.you11.redditimageviewer.api.ApiService
import ru.you11.redditimageviewer.api.IApiMethods
import ru.you11.redditimageviewer.api.RetrofitFactory
import ru.you11.redditimageviewer.model.RedditPost

class ViewerRepository {

    private val retrofit = RetrofitFactory().create()
    private val apiService = ApiService(retrofit.create(IApiMethods::class.java))
}