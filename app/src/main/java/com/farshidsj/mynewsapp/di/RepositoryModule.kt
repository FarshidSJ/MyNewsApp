package com.farshidsj.mynewsapp.di

import com.farshidsj.mynewsapp.api.ApiService
import com.farshidsj.mynewsapp.database.NewsDao
import com.farshidsj.mynewsapp.repositories.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        apiService: ApiService,
        newsDao: NewsDao
    ) : MainRepository {
        return MainRepository(apiService, newsDao)
    }
}