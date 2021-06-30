package com.farshidsj.mynewsapp.di

import android.content.Context
import androidx.room.Room
import com.farshidsj.mynewsapp.database.NewsDao
import com.farshidsj.mynewsapp.database.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideNewsDB(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            NewsDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsDAO(NewsDatabase: NewsDatabase): NewsDao {
        return NewsDatabase.newsDao()
    }

}