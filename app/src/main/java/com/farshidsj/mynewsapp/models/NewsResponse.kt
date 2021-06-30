package com.farshidsj.mynewsapp.models

import com.farshidsj.mynewsapp.models.Article

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)