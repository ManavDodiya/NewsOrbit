package com.newsorbit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity representing a cached news article.
 * Uses article URL as primary key since it's unique.
 */
@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey
    val url: String,
    
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val source: String,
    val author: String?,
    val publishedAt: String,
    val content: String?,
    
    // Timestamp for cache management
    val cachedAt: Long = System.currentTimeMillis()
)
