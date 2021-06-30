package com.farshidsj.mynewsapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.farshidsj.mynewsapp.models.Article
import com.farshidsj.mynewsapp.models.NewsResponse
import com.farshidsj.mynewsapp.models.pagingsources.BreakingNewsPagingSource
import com.farshidsj.mynewsapp.models.pagingsources.SearchNewsPagingSource
import com.farshidsj.mynewsapp.repositories.MainRepository
import com.farshidsj.mynewsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class NewsViewModel
@Inject
constructor(
    private val repository: MainRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNewsResponse: NewsResponse? = null

    fun getBreakingNews(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 15
            ),
            pagingSourceFactory = {BreakingNewsPagingSource(repository)}
        ).flow.cachedIn(viewModelScope)
    }

    fun searchNews(searchQuery: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 15
            ),
            pagingSourceFactory = {SearchNewsPagingSource(repository, searchQuery)}
        ).flow.cachedIn(viewModelScope)
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.upsert(article)
    }

    fun getSavedNews() = repository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.deleteArticle(article)
    }


}