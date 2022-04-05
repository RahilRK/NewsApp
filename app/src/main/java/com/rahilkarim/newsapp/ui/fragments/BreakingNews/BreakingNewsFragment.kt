package com.rahilkarim.newsapp.ui.fragments.BreakingNews

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahilkarim.newsapp.R
import com.rahilkarim.newsapp.network.Resource
import com.rahilkarim.newsapp.util.Application
import com.rahilkarim.newsapp.util.GlobalClass
import com.rahilkarim.newsapp.util.Repository
import kotlinx.android.synthetic.main.fragment_breaking_news.*

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    /*override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breaking_news, container, false)
    }*/

    val TAG = "BreakingNewsFragment"

    lateinit var globalClass: GlobalClass
    lateinit var repository: Repository

    lateinit var viewModel: BreakingNewsViewModel
    lateinit var mAdapter: BreakingNewsAdapter

    var offset = 0
    var limit = 6
    var totalResults = 0
    var isLoading = false
    var isScrolling = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        val breakingNewsVPF = BreakingNewsVPF(repository)
        viewModel = ViewModelProvider(this,breakingNewsVPF)
            .get(BreakingNewsViewModel::class.java)

        onClick()
        setAdapter()
        getData()
    }

    fun init() {
        globalClass = (requireActivity().application as Application).globalClass
        repository = (requireActivity().application as Application).repository
    }

    fun onClick() {
        rvBreakingNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
                else if(newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    isScrolling = false
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = rvBreakingNews.layoutManager as LinearLayoutManager

                val visibleItemCount: Int = layoutManager.getChildCount()
                val totalItem: Int = layoutManager.getItemCount()
                val lastVisibleItem: Int = layoutManager.findLastVisibleItemPosition()
                val firstVisiblesItem: Int = layoutManager.findFirstVisibleItemPosition()

                globalClass.log(TAG, "recyclerView_totalItem: $totalItem")
//                globalClass.log(TAG, "recyclerView_lastVisibleItem: $lastVisibleItem")
//                globalClass.log(TAG, "offset: ${offset - 1}")

                globalClass.log(TAG,"isLoading:${isLoading}")
                globalClass.log(TAG,"isScrolling:${isScrolling}")
                if (lastVisibleItem == offset - 1 && !isLoading && isScrolling) {
//                    offset = lastVisibleItem + 1
                    globalClass.log(TAG,"load more")
                    viewModel.getBreakingNews("us")
                }
            }
        })
    }

    fun setAdapter() {
        mAdapter = BreakingNewsAdapter()
        rvBreakingNews.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        mAdapter.setOnItemClickListener {

            val model = it
            val action =
                BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(model)
            findNavController().navigate(action)
        }
    }

    fun getData() {
        viewModel.breakingNews.observe(viewLifecycleOwner) { response ->

            when (response) {
                is Resource.Success -> {

                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        mAdapter.differ.submitList(newsResponse.articles.toList())
                        totalResults = newsResponse.totalResults
                        offset = mAdapter.differ.currentList.size
//                        offset = offset + limit
                    }
                }
                is Resource.Error -> {

                    hideProgressBar()
                    response.message?.let { message ->

                        globalClass.log(TAG, "getData Error: ${message}")
                        globalClass.toastshort("Response Error")
                    }
                }
                is Resource.Loading -> {

                    showProgressBar()
                }
            }
        }
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }
}