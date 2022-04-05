package com.rahilkarim.newsapp.ui.fragments.BreakingNews

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


class BreakingNewsViewModel(
    val repository: Repository
): ViewModel() {

    val TAG = "BreakingNewsViewModel"

    val breakingNews: MutableLiveData<Resource<BreakingNews>> = MutableLiveData()
    var page = 1

    var breakingNewsResponse : BreakingNews? = null

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(country: String) = viewModelScope.launch {

        Log.e(TAG, "getBreakingNews: ")
        breakingNews.postValue(Resource.Loading())
        val response = repository.getBreakingNews(country,page)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<BreakingNews>): Resource<BreakingNews> {

        if(response.isSuccessful) {

            response.body()?.let { result ->

                page++
                Log.e(TAG, "page: ${page}")

                if(breakingNewsResponse == null) {

                    //todo for first time
                    breakingNewsResponse = result
                }
                else {

                    //todo rest of the time
                    val oldList = breakingNewsResponse?.articles
                    val newList = result.articles
                    oldList?.addAll(newList)
                }

                return Resource.Success(breakingNewsResponse?:result)
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