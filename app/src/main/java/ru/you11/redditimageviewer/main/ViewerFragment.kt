package ru.you11.redditimageviewer.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.you11.redditimageviewer.R

class ViewerFragment : Fragment() {

    private lateinit var viewModel: ViewerViewModel

    private lateinit var imagesRV: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = createViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_main, container, false)
        with(root) {
            imagesRV = findViewById(R.id.images_rv)
            val numberOfColumns = 2
            imagesRV.layoutManager = StaggeredGridLayoutManager(numberOfColumns, RecyclerView.VERTICAL)
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        loadUrls()
    }

    private fun loadUrls() {
        viewModel.getUrls().observe(this, Observer {
            val adapter = ViewerRVAdapter(ArrayList(it))
            imagesRV.adapter = adapter

        })
    }

    private fun createViewModel(): ViewerViewModel {
        return ViewModelProviders.of(this).get(ViewerViewModel::class.java)
    }
}