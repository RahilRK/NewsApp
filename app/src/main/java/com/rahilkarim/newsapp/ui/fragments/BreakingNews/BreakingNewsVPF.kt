package com.rahilkarim.newsapp.ui.fragments.BreakingNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rahilkarim.newsapp.util.Repository

class BreakingNewsVPF(
    val repository: Repository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BreakingNewsViewModel(repository) as T
    }
}