package com.rahilkarim.newsapp.network

import com.rahilkarim.newsapp.models.BreakingNews
import com.rahilkarim.newsapp.util.Constants.Companion.APIKEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        country: String = "us",
        @Query("page")
        page: Int = 1,
//        @Query("pageSize")
//        pageSize: Int = 10,
        @Query("apiKey")
        apiKey: String = APIKEY,
    ): Response<BreakingNews>

    @GET("v2/everything")
    suspend fun getSearchNews(
        @Query("q")
        searchKeyWord: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = APIKEY
    ): Response<BreakingNews>

}