package ru.you11.redditimageviewer.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IApiMethods {

    @GET("{subreddit}/hot")
    fun getPosts(
        @Path("subreddit") subreddit: String,
        @Query("limit") limit: Int
    ): Call<ApiPost>

    @GET("/r/{subreddit}/hot.json")
    fun getTopAfter(
        @Path("subreddit") subreddit: String,
        @Query("after") after: String,
        @Query("limit") limit: Int
    ): Call<ApiPost>

    @GET("/r/{subreddit}/hot.json")
    fun getTopBefore(
        @Path("subreddit") subreddit: String,
        @Query("before") before: String,
        @Query("limit") limit: Int
    ): Call<ApiPost>
}