package com.rahilkarim.newsapp.ui.fragments.SavedNews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.rahilkarim.newsapp.R
import com.rahilkarim.newsapp.ui.MainActivity
import com.rahilkarim.newsapp.ui.fragments.BreakingNews.BreakingNewsViewModel
import com.rahilkarim.newsapp.util.Application
import com.rahilkarim.newsapp.util.GlobalClass
import com.rahilkarim.newsapp.util.Repository

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    /*override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_news, container, false)
    }*/

    val TAG = "SavedNewsFragment"

    lateinit var globalClass: GlobalClass
    lateinit var repository: Repository

    lateinit var viewModel: SavedNewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        val saveNewsVPF = SavedNewsVPF(repository)
        viewModel = ViewModelProvider(this,saveNewsVPF)
            .get(SavedNewsViewModel::class.java)

        getData()
    }

    fun init() {
        globalClass = (requireActivity().application as Application).globalClass
        repository = (requireActivity().application as Application).repository
    }

    fun getData() {
        viewModel.getSavedNews().observe(viewLifecycleOwner) { articles ->

            globalClass.log(TAG,"Total saved articles: ${articles.size}")
            globalClass.log(TAG,"${articles}")
        }
    }
}