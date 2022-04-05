package com.rahilkarim.newsapp.models

import com.rahilkarim.newsapp.models.Article

data class BreakingNews(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)