package com.farshidsj.mynewsapp.repositories

import com.farshidsj.mynewsapp.api.ApiService
import com.farshidsj.mynewsapp.database.NewsDao
import com.farshidsj.mynewsapp.models.Article

class MainRepository
constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao
){
    suspend fun getBreakingNews(countryCode: String, pageNumber:Int) =
        apiService.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        apiService.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = newsDao.upsert(article)

    fun getSavedNews() = newsDao.getAllArticles()

    suspend fun deleteArticle(article: Article) = newsDao.deleteArticle(article)
}