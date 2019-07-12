package ru.you11.redditimageviewer.api

interface IApiService {

    fun getInitialPosts(subreddit: String): ApiPost

    fun getAfterPosts(subreddit: String, after: String): ApiPost
}