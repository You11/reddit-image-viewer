package ru.you11.redditimageviewer.api

import ru.you11.redditimageviewer.RedditPost

class ApiPost {

    val data: Data? = null

    class Data {

        val children: List<Children>? = null

        class Children {

            val data: Data? = null

            class Data {

                val title: String? = null
            }
        }
    }

    companion object {
        fun convertToPostList(apiPosts: ApiPost): List<RedditPost> {

            return apiPosts.data?.children?.map { convertToPost(it) } ?: ArrayList()
        }

        private fun convertToPost(apiPost: Data.Children): RedditPost {
            return RedditPost(apiPost.data?.title ?: "")
        }
    }
}