package com.newsorbit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.newsorbit.data.local.dao.ArticleDao
import com.newsorbit.data.local.entity.ArticleEntity

/**
 * Room database for NewsOrbit app.
 * Contains articles table for offline caching.
 */
@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    
    /**
     * Provides access to ArticleDao.
     */
    abstract fun articleDao(): ArticleDao
    
    companion object {
        const val DATABASE_NAME = "news_database"
    }
}
