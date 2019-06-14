package ru.you11.redditimageviewer.api

interface IApiService {

    fun getPosts(subreddit: String): ApiPost

}