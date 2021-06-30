package com.farshidsj.mynewsapp.models.pagingsources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.farshidsj.mynewsapp.models.Article
import com.farshidsj.mynewsapp.repositories.MainRepository
import java.lang.Exception

class SearchNewsPagingSource(
    private val repository: MainRepository,
    private val searchQuery: String
) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1

        return try {
            val data = repository.searchNews(searchQuery, page)

            LoadResult.Page(
                data = data.body()?.articles!!,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.body()?.articles?.isEmpty()!!) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}