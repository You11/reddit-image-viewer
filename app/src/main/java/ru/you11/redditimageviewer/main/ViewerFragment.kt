package ru.you11.redditimageviewer.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.you11.redditimageviewer.R
import ru.you11.redditimageviewer.model.RedditPost
import java.util.concurrent.Executors

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

            val adapter = ViewerRVAdapter()
            imagesRV.adapter = adapter

            updateAdapter("pics")
        }

        return root
    }

    private fun updateAdapter(subreddit: String) {
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(20)
            .setPageSize(50)
            .setEnablePlaceholders(true)
            .build()

        val pagedListBuilder = LivePagedListBuilder(ViewerDataSourceFactory(subreddit), config)
            .setFetchExecutor(Executors.newFixedThreadPool(5))
            .build()

        pagedListBuilder.observe(this@ViewerFragment, Observer {
            (imagesRV.adapter as ViewerRVAdapter).submitList(it)
        })
    }

    override fun onResume() {
        super.onResume()
        setupDataObservers()
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
                if (subreddit.isNullOrBlank()) return false
                updateAdapter(subreddit)
                return true
            }
        })
    }

    private fun setupDataObservers() {
        setupCurrentSubredditObserver()
    }

    private fun setupCurrentSubredditObserver() {
        viewModel.currentSubreddit.observe(this, Observer {
            changeTitle(it)
        })
    }

    private fun createViewModel(): ViewerViewModel {
        return ViewModelProviders.of(this).get(ViewerViewModel::class.java)
    }

    private fun changeTitle(title: String) {
        activity?.title = "r/$title"
    }
}