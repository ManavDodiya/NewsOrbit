package com.newsorbit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newsorbit.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for article database operations.
 */
@Dao
interface ArticleDao {
    
    /**
     * Insert or update articles in the database.
     * Uses REPLACE strategy to update existing articles.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)
    
    /**
     * Get all cached articles as a Flow for reactive updates.
     * Ordered by cached timestamp (newest first).
     */
    @Query("SELECT * FROM articles ORDER BY cachedAt DESC")
    fun getAllArticles(): Flow<List<ArticleEntity>>
    
    /**
     * Clear all articles from the database.
     * Used for refresh operations.
     */
    @Query("DELETE FROM articles")
    suspend fun clearAllArticles()
    
    /**
     * Get count of cached articles.
     */
    @Query("SELECT COUNT(*) FROM articles")
    suspend fun getArticleCount(): Int
}
