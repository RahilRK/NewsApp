package com.rahilkarim.newsapp.ui.fragments.SavedNews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rahilkarim.newsapp.models.BreakingNews
import com.rahilkarim.newsapp.models.ApiErrorMessage
import com.rahilkarim.newsapp.models.Article
import com.rahilkarim.newsapp.network.Resource
import com.rahilkarim.newsapp.util.Repository
import kotlinx.coroutines.launch
import retrofit2.Response


class SavedNewsViewModel(
    val repository: Repository
): ViewModel() {

    val TAG = "SavedNewsViewModel"

    fun saveNews(article: Article) {
        viewModelScope.launch {
            repository.saveNews(article)
        }
    }

    fun getSavedNews(): LiveData<List<Article>> {
        return repository.getSavedNews()
    }
}