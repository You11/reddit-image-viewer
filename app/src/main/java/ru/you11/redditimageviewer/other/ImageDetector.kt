package ru.you11.redditimageviewer.other

class ImageDetector {

    fun isUrlContainsImageExtensions(url: String): Boolean {
        return url.endsWith("jpg") || url.endsWith("jpeg") || url.endsWith("png") || url.endsWith("gif")
    }
}