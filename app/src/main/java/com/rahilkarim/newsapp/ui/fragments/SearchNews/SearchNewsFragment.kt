package com.rahilkarim.newsapp.ui.fragments.SearchNews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahilkarim.newsapp.R
import com.rahilkarim.newsapp.network.Resource
import com.rahilkarim.newsapp.ui.MainActivity
import com.rahilkarim.newsapp.ui.fragments.BreakingNews.BreakingNewsAdapter
import com.rahilkarim.newsapp.ui.fragments.BreakingNews.BreakingNewsVPF
import com.rahilkarim.newsapp.ui.fragments.BreakingNews.BreakingNewsViewModel
import com.rahilkarim.newsapp.util.Application
import com.rahilkarim.newsapp.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.rahilkarim.newsapp.util.GlobalClass
import com.rahilkarim.newsapp.util.Repository
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_breaking_news.paginationProgressBar
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    /*override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_news, container, false)
    }*/

    val TAG = "BreakingNewsFragment"

    lateinit var globalClass: GlobalClass
    lateinit var repository: Repository

    lateinit var viewModel: SearchNewsViewModel
    lateinit var mAdapter: SearchNewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        val searchNewsVPF = SearchNewsVPF(repository)
        viewModel = ViewModelProvider(this,searchNewsVPF)
            .get(SearchNewsViewModel::class.java)

        onClick()
        setAdapter()
        getData()
    }

    fun init() {
        globalClass = (requireActivity().application as Application).globalClass
        repository = (requireActivity().application as Application).repository
    }

    fun onClick() {

        var job: Job? = null
        etSearch.addTextChangedListener { editable ->

            job?.cancel()

            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if(editable.toString().isNotBlank()) {
                        viewModel.getSearchNews(editable.toString())
                    }
                }
            }

        }
    }

    fun setAdapter() {
        mAdapter = SearchNewsAdapter()
        rvSearchNews.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    fun getData() {
        viewModel.searchNews.observe(viewLifecycleOwner) { response ->

            when (response) {
                is Resource.Success -> {

                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        mAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {

                    hideProgressBar()
                    response.message?.let { message ->

                        globalClass.log(TAG, message)
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
//        isLoading = false
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
//        isLoading = true
    }
}