package com.rahilkarim.newsapp.ui.fragments.SearchNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rahilkarim.newsapp.util.Repository

class SearchNewsVPF(
    val repository: Repository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchNewsViewModel(repository) as T
    }
}