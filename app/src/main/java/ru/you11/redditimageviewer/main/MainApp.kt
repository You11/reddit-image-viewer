package ru.you11.redditimageviewer.main

import android.app.Application
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader

class MainApp: Application() {

    override fun onCreate() {
        super.onCreate()
        BigImageViewer.initialize(GlideImageLoader.with(applicationContext))
    }
}