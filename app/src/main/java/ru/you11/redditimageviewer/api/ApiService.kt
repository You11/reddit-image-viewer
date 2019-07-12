package ru.you11.redditimageviewer.api

import retrofit2.Call

class ApiService(private val apiMethods: IApiMethods): IApiService {

    private val limit = 50

    override fun getInitialPosts(subreddit: String): ApiPost {
        return getApiResponse(apiMethods.getPosts(subreddit, limit))
    }

    override fun getAfterPosts(subreddit: String, after: String): ApiPost {
        return getApiResponse(apiMethods.getTopAfter(subreddit, after, limit))
    }

    private fun <T> getApiResponse(call: Call<T>): T {
        val response = call.execute()

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("response error")
        } else throw Exception("response error")
    }
}