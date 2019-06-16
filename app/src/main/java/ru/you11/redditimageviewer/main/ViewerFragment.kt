package ru.you11.redditimageviewer.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.you11.redditimageviewer.R

class ViewerFragment : Fragment() {

    private lateinit var viewModel: ViewerViewModel

    private lateinit var imagesRV: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = createViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_main, container, false)
        with(root) {
            imagesRV = findViewById(R.id.images_rv)
            val numberOfColumns = 2
            imagesRV.layoutManager = StaggeredGridLayoutManager(numberOfColumns, RecyclerView.VERTICAL)
            val adapter = ViewerRVAdapter(ArrayList())
            imagesRV.adapter = adapter
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        loadUrlsIntoRV()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_viewer, menu)

        val item = menu?.findItem(R.id.viewer_search)
        val view = item?.actionView as SearchView

        view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(subreddit: String?): Boolean {
                viewModel.getUrls(subreddit ?: "")
                return true
            }
        })
    }

    private fun loadUrlsIntoRV() {
        viewModel.getUrls("pics").observe(this, Observer {
            (imagesRV.adapter as ViewerRVAdapter).updateData(ArrayList(it))
        })
    }

    private fun createViewModel(): ViewerViewModel {
        return ViewModelProviders.of(this).get(ViewerViewModel::class.java)
    }
}