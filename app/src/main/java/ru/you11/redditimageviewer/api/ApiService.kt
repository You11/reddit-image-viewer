package ru.you11.redditimageviewer.api

import android.util.Log
import retrofit2.Call

class ApiService(private val apiMethods: IApiMethods): IApiService {

    private val limit = 50

    override fun getInitialPosts(subreddit: String): AppResult<ApiPost> {
        return getApiResponse(apiMethods.getPosts(subreddit, limit))
    }

    override fun getAfterPosts(subreddit: String, after: String): AppResult<ApiPost> {
        return getApiResponse(apiMethods.getTopAfter(subreddit, after, limit))
    }

    private fun <T> getApiResponse(call: Call<T>): AppResult<T> {
        val response = call.execute()

        return if (response.isSuccessful) {
            val responseBody = response.body()

            if (responseBody != null) AppResult(responseBody) else {
                getEmptyBodyError()
            }
        } else {
            AppResult(response.code().toString())
        }
    }

    private fun <T>getEmptyBodyError(): AppResult<T> = AppResult("empty body")
}