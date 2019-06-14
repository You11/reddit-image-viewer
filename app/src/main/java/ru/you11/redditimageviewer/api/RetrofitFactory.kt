package ru.you11.redditimageviewer.api

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory {

    private val timeoutTimeInSeconds = 10L
    private val redditBaseUrl = "https://api.reddit.com/r/"

    fun create(): Retrofit {

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(timeoutTimeInSeconds, TimeUnit.SECONDS)
            .readTimeout(timeoutTimeInSeconds, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .baseUrl(redditBaseUrl)
            .build()
    }
}