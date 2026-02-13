package com.newsorbit.di

import android.content.Context
import androidx.room.Room
import com.newsorbit.data.local.NewsDatabase
import com.newsorbit.data.local.dao.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module providing database-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    /**
     * Provides Room database instance.
     */
    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            NewsDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    
    /**
     * Provides ArticleDao from database.
     */
    @Provides
    @Singleton
    fun provideArticleDao(database: NewsDatabase): ArticleDao {
        return database.articleDao()
    }
}
