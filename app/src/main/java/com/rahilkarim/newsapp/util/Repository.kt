package com.rahilkarim.newsapp.util

import androidx.lifecycle.LiveData
import com.rahilkarim.newsapp.database.Database
import com.rahilkarim.newsapp.models.Article
import com.rahilkarim.newsapp.models.BreakingNews
import com.rahilkarim.newsapp.network.RetrofitClient
import retrofit2.Response

class Repository(
    val db: Database,
) {
    suspend fun getBreakingNews(country: String, page: Int): Response<BreakingNews> {
        return RetrofitClient.apiCall.getBreakingNews(country,page)
    }

    suspend fun getSearchNews(searchKeyWord: String, page: Int): Response<BreakingNews> {
        return RetrofitClient.apiCall.getSearchNews(searchKeyWord,page)
    }

    suspend fun saveNews(article: Article): Long {

        return db.contactdao().saveNews(article)
    }

    fun getSavedNews(): LiveData<List<Article>> {
         return db.contactdao().getSavedNews()
    }
}