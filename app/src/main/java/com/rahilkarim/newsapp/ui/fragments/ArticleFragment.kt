package com.rahilkarim.newsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.rahilkarim.newsapp.R
import com.rahilkarim.newsapp.ui.MainActivity
import com.rahilkarim.newsapp.ui.fragments.BreakingNews.BreakingNewsVPF
import com.rahilkarim.newsapp.ui.fragments.BreakingNews.BreakingNewsViewModel
import com.rahilkarim.newsapp.ui.fragments.SavedNews.SavedNewsVPF
import com.rahilkarim.newsapp.ui.fragments.SavedNews.SavedNewsViewModel
import com.rahilkarim.newsapp.util.Application
import com.rahilkarim.newsapp.util.GlobalClass
import com.rahilkarim.newsapp.util.Repository
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(R.layout.fragment_article) {

/*
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false)
    }
*/

    val TAG = "ArticleFragment"

    lateinit var globalClass: GlobalClass
    lateinit var repository: Repository

    lateinit var viewModel: SavedNewsViewModel

    val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        val saveNewsVPF = SavedNewsVPF(repository)
        viewModel = ViewModelProvider(this,saveNewsVPF)
            .get(SavedNewsViewModel::class.java)

        val model = args.articleModelArgs
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(model.url!!)
        }
        fab.setOnClickListener {
            viewModel.saveNews(model)
            Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }
    }

    fun init() {
        globalClass = (requireActivity().application as Application).globalClass
        repository = (requireActivity().application as Application).repository
    }
}