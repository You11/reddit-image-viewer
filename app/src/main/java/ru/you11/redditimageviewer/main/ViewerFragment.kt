package ru.you11.redditimageviewer.main

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.piasy.biv.view.BigImageView
import com.github.piasy.biv.view.GlideImageViewFactory
import ru.you11.redditimageviewer.R
import ru.you11.redditimageviewer.other.Consts
import java.util.concurrent.Executors

class ViewerFragment : Fragment(), OnImageClickListener {

    private lateinit var viewModel: ViewerViewModel

    private lateinit var imagesRV: RecyclerView
    private lateinit var emptyView: View
    private lateinit var bigImageView: BigImageView
    private lateinit var swipeToRefresh: SwipeRefreshLayout

    private lateinit var searchButton: MenuItem
    private lateinit var closeButton: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = createViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val startingSubreddit = Consts.startingSubreddit

        val root = inflater.inflate(R.layout.fragment_main, container, false)
        with(root) {
            bigImageView = findViewById(R.id.images_big)
            bigImageView.setImageViewFactory(GlideImageViewFactory())

            emptyView = findViewById(R.id.images_empty)

            imagesRV = findViewById(R.id.images_rv)
            val numberOfColumns = 2
            imagesRV.layoutManager = StaggeredGridLayoutManager(numberOfColumns, RecyclerView.VERTICAL)

            val adapter = ViewerRVAdapter(this@ViewerFragment)
            imagesRV.adapter = adapter

            setPagedListAndTitle(startingSubreddit)

            swipeToRefresh = findViewById(R.id.viewer_swipe_refresh_layout)
            swipeToRefresh.setOnRefreshListener {
                setPagedListAndTitle(viewModel.currentSubreddit.value ?: startingSubreddit)
            }
        }

        return root
    }

    private fun setPagedListAndTitle(subreddit: String) {

        viewModel.changeLoadingStatus(LoadingStatus.LOADING)
        viewModel.updateSubreddit(subreddit)

        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(20)
            .setPageSize(50)
            .setEnablePlaceholders(true)
            .build()

        val pagedListBuilder = LivePagedListBuilder(ViewerDataSourceFactory(subreddit, viewModel), config)
            .setFetchExecutor(Executors.newFixedThreadPool(5))
            .build()

        pagedListBuilder.observe(this@ViewerFragment, Observer {

            val loadingStatus = if (it.isEmpty())
                LoadingStatus.EMPTY
            else
                LoadingStatus.LOADED

            viewModel.changeLoadingStatus(loadingStatus)
            (imagesRV.adapter as ViewerRVAdapter).submitList(it)
        })
    }

    override fun onResume() {
        super.onResume()
        setupCurrentSubredditObserver()
        setupErrorObserver()
        setupLoadingStatusObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_viewer, menu)

        setupSearchButton(menu)
        setupCloseButton(menu)
    }

    private fun setupLoadingStatusObserver() {
        viewModel.loadingStatus.observe(this, Observer {
            when (it) {
                LoadingStatus.LOADING -> showLoading()
                LoadingStatus.EMPTY -> showEmptyRV()
                LoadingStatus.LOADED -> showLoadedRV()
            }
        })
    }

    private fun setupErrorObserver() {
        viewModel.error.observe(this, Observer {
            onError(it)
        })
    }

    private fun setupCloseButton(menu: Menu) {
        closeButton = menu.findItem(R.id.viewer_close)
        closeButton.setOnMenuItemClickListener {
            changeMenuButtonsVisibility(isImageOpen = false)
            closeImageView()
            return@setOnMenuItemClickListener true
        }
    }

    private fun closeImageView() {
        bigImageView.removeAllViews()
        bigImageView.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun setupSearchButton(menu: Menu) {
        searchButton = menu.findItem(R.id.viewer_search)
        val view = searchButton.actionView as SearchView

        view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(subreddit: String?): Boolean {
                if (subreddit.isNullOrBlank() || viewModel.loadingStatus.value == LoadingStatus.LOADING) return false
                setPagedListAndTitle(filterSubredditNameFromInvalidInput(subreddit))
                return true
            }
        })
    }

    private fun filterSubredditNameFromInvalidInput(subreddit: String): String =
        subreddit.replace(Regex("[^a-zA-Z0-9_]*"), "")

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

    override fun onImageClick(url: String) {
        changeMenuButtonsVisibility(isImageOpen = true)
        openImageView(url)
        if (searchButton.isActionViewExpanded) searchButton.collapseActionView()
    }

    private fun openImageView(url: String) {
        bigImageView.showImage(Uri.parse(url))
        bigImageView.setBackgroundColor(Color.BLACK)
    }

    private fun changeMenuButtonsVisibility(isImageOpen: Boolean) {
        searchButton.isVisible = !isImageOpen
        closeButton.isVisible = isImageOpen
    }

    private fun onError(message: String) {
        Toast.makeText(activity, resources.getString(R.string.error_message_template, message), Toast.LENGTH_SHORT)
            .show()
    }

    private fun showLoading() {
        swipeToRefresh.isRefreshing = true
    }

    private fun showEmptyRV() {
        imagesRV.visibility = View.GONE
        emptyView.visibility = View.VISIBLE
        swipeToRefresh.isRefreshing = false
    }

    private fun showLoadedRV() {
        emptyView.visibility = View.GONE
        imagesRV.visibility = View.VISIBLE
        swipeToRefresh.isRefreshing = false
    }
}