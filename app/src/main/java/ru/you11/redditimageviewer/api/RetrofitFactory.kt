package ru.you11.redditimageviewer.api

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.you11.redditimageviewer.other.Consts
import java.util.concurrent.TimeUnit

class RetrofitFactory {

    private val redditBaseUrl = "https://api.reddit.com/r/"

    fun create(): Retrofit {

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(Consts.timeoutLength, TimeUnit.SECONDS)
            .readTimeout(Consts.timeoutLength, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .baseUrl(redditBaseUrl)
            .build()
    }
}