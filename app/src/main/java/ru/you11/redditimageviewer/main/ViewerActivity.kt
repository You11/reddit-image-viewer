package ru.you11.redditimageviewer.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.you11.redditimageviewer.R

class ViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            ViewerFragment()
        ).commit()
    }
}
