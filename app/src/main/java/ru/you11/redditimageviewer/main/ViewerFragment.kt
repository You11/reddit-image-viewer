package ru.you11.redditimageviewer.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.you11.redditimageviewer.R
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

            val config = PagedList.Config.Builder().setInitialLoadSizeHint(20).setPageSize(50).setEnablePlaceholders(true).build()

            val adapter = ViewerRVAdapter()
            imagesRV.adapter = adapter

            val pagedList = LivePagedListBuilder(ViewerDataSourceFactory("pics"), config).setFetchExecutor(Executors.newFixedThreadPool(5)).build()
            pagedList.observe(this@ViewerFragment, Observer {
                adapter.submitList(it)
            })
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        setupDataObservers()
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
                if (subreddit.isNullOrBlank()) return false
//                viewModel.getInitialUrls(subreddit)
                return true
            }
        })
    }

    private fun setupDataObservers() {
//        setupUrlsObserver()
        setupCurrentSubredditObserver()
    }

//    private fun setupUrlsObserver() {
//        viewModel.posts.observe(this, Observer {
//            (imagesRV.adapter as ViewerRVAdapter).submitList(it)
//        })
//    }

    private fun setupCurrentSubredditObserver() {
        viewModel.currentSubreddit.observe(this, Observer {
            changeTitle(it)
        })
    }

    private fun loadUrlsIntoRV() {
//        viewModel.getInitialUrls("pics")
    }

    private fun createViewModel(): ViewerViewModel {
        return ViewModelProviders.of(this).get(ViewerViewModel::class.java)
    }

    private fun changeTitle(title: String) {
        activity?.title = "r/$title"
    }
}