package com.newsorbit.data.repository

import com.newsorbit.BuildConfig
import com.newsorbit.data.local.dao.ArticleDao
import com.newsorbit.data.local.entity.ArticleEntity
import com.newsorbit.data.remote.api.NewsApiService
import com.newsorbit.data.remote.dto.ArticleDto
import com.newsorbit.domain.model.Article
import com.newsorbit.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing news articles.
 * Implements single source of truth pattern:
 * - Fetches from network and caches to local database
 * - Returns cached data when offline
 * - Handles pagination
 */
@Singleton
class NewsRepository @Inject constructor(
    private val apiService: NewsApiService,
    private val articleDao: ArticleDao
) {
    
    /**
     * Fetch articles with pagination support.
     * 
     * @param page Page number (1-indexed)
     * @param forceRefresh If true, clears cache and fetches fresh data
     * @return Flow emitting Result states (Loading, Success, Error)
     */
    fun getArticles(page: Int = 1, forceRefresh: Boolean = false): Flow<Result<List<Article>>> = flow {
        emit(Result.Loading)
        
        try {
            // If first page and force refresh, clear cache
            if (page == 1 && forceRefresh) {
                articleDao.clearAllArticles()
            }
            
            // Fetch from API
            val response = apiService.getTopHeadlines(
                page = page,
                apiKey = BuildConfig.NEWS_API_KEY
            )
            
            // Map DTOs to entities and cache them
            val entities = response.articles.map { it.toEntity() }
            articleDao.insertArticles(entities)
            
            // Return success with domain models
            val articles = entities.map { it.toDomainModel() }
            emit(Result.Success(articles))
            
        } catch (e: Exception) {
            // On error, try to return cached data
            val cachedArticles = getCachedArticles()
            
            if (cachedArticles.isNotEmpty()) {
                // Return cached data with error indication
                emit(Result.Success(cachedArticles))
            } else {
                // No cached data available, emit error
                val errorMessage = when {
                    e.message?.contains("Unable to resolve host") == true -> 
                        "Network error. Please check your connection."
                    e.message?.contains("HTTP 401") == true -> 
                        "Invalid API key. Please check your configuration."
                    e.message?.contains("HTTP 429") == true -> 
                        "API rate limit exceeded. Please try again later."
                    else -> 
                        "Failed to load articles: ${e.message ?: "Unknown error"}"
                }
                emit(Result.Error(errorMessage))
            }
        }
    }
    
    /**
     * Get cached articles from local database.
     */
    private suspend fun getCachedArticles(): List<Article> {
        return try {
            articleDao.getAllArticles()
                .map { entityList -> entityList.map { it.toDomainModel() } }
                .first()
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    /**
     * Get cached articles as Flow for reactive UI updates.
     */
    fun getCachedArticlesFlow(): Flow<List<Article>> {
        return articleDao.getAllArticles().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
}

/**
 * Extension function to convert ArticleDto to ArticleEntity.
 */
private fun ArticleDto.toEntity(): ArticleEntity {
    return ArticleEntity(
        url = this.url,
        title = this.title,
        description = this.description,
        imageUrl = this.urlToImage,
        source = this.source.name,
        author = this.author,
        publishedAt = this.publishedAt,
        content = this.content
    )
}

/**
 * Extension function to convert ArticleEntity to domain Article model.
 */
private fun ArticleEntity.toDomainModel(): Article {
    return Article(
        url = this.url,
        title = this.title,
        description = this.description ?: "",
        imageUrl = this.imageUrl,
        source = this.source,
        author = this.author,
        publishedAt = this.publishedAt,
        content = this.content
    )
}
