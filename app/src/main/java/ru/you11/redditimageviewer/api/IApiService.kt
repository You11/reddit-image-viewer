package ru.you11.redditimageviewer.api

interface IApiService {

    fun getInitialPosts(subreddit: String): AppResult<ApiPost>

    fun getAfterPosts(subreddit: String, after: String): AppResult<ApiPost>
}