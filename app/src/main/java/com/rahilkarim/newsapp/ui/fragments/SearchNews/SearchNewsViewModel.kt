package com.rahilkarim.newsapp.ui.fragments.SearchNews

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rahilkarim.newsapp.models.BreakingNews
import com.rahilkarim.newsapp.models.ApiErrorMessage
import com.rahilkarim.newsapp.network.Resource
import com.rahilkarim.newsapp.util.Repository
import kotlinx.coroutines.launch
import retrofit2.Response


class SearchNewsViewModel(
    val repository: Repository
): ViewModel() {

    val TAG = "SearchNewsViewModel"

    val searchNews: MutableLiveData<Resource<BreakingNews>> = MutableLiveData()
    val searchNewsPage = 1

    fun getSearchNews(searchKeyWord: String) = viewModelScope.launch {

        searchNews.postValue(Resource.Loading())
        val response = repository.getSearchNews(searchKeyWord,searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleSearchNewsResponse(response: Response<BreakingNews>): Resource<BreakingNews> {

        if(response.isSuccessful) {

            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }

        val gson = Gson()
        val apiErrorModel: ApiErrorMessage = gson.fromJson(
            response.errorBody()!!.charStream(),
            ApiErrorMessage::class.java
        )
        return Resource.Error(apiErrorModel.message)
    }
}