package ru.you11.redditimageviewer.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IApiMethods {

    @GET("{subreddit}/hot")
    fun getPosts(@Path("subreddit") subreddit: String): Call<ApiPost>
}