package ru.you11.redditimageviewer.api

import retrofit2.Call

class ApiService(private val apiMethods: IApiMethods): IApiService {

    override fun getPosts(subreddit: String): ApiPost {
        return getApiResponse(apiMethods.getPosts(subreddit))
    }

    private fun <T> getApiResponse(call: Call<T>): T {
        val response = call.execute()

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("response error")
        } else throw Exception("response error")
    }
}