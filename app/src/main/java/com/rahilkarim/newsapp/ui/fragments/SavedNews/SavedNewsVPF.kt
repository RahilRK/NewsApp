package com.rahilkarim.newsapp.ui.fragments.SavedNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rahilkarim.newsapp.util.Repository

class SavedNewsVPF(
    val repository: Repository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SavedNewsViewModel(repository) as T
    }
}