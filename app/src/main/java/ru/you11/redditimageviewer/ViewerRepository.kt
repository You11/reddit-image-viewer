package ru.you11.redditimageviewer

import ru.you11.redditimageviewer.api.ApiPost
import ru.you11.redditimageviewer.api.ApiService
import ru.you11.redditimageviewer.api.IApiMethods
import ru.you11.redditimageviewer.api.RetrofitFactory

class ViewerRepository {

    private val retrofit = RetrofitFactory().create()
    private val apiService = ApiService(retrofit.create(IApiMethods::class.java))

    fun getPosts(): List<RedditPost> {
        val posts = apiService.getPosts("pics")
        return ApiPost.convertToPostList(posts)
    }

}