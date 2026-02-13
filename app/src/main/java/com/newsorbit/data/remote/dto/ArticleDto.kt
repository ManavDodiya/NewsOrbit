package com.newsorbit.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object for NewsAPI response.
 * Represents the top-level response from the API.
 */
data class NewsResponseDto(
    @SerializedName("status")
    val status: String,
    
    @SerializedName("totalResults")
    val totalResults: Int,
    
    @SerializedName("articles")
    val articles: List<ArticleDto>
)

/**
 * Data Transfer Object for individual article from NewsAPI.
 */
data class ArticleDto(
    @SerializedName("source")
    val source: SourceDto,
    
    @SerializedName("author")
    val author: String?,
    
    @SerializedName("title")
    val title: String,
    
    @SerializedName("description")
    val description: String?,
    
    @SerializedName("url")
    val url: String,
    
    @SerializedName("urlToImage")
    val urlToImage: String?,
    
    @SerializedName("publishedAt")
    val publishedAt: String,
    
    @SerializedName("content")
    val content: String?
)

/**
 * Data Transfer Object for news source.
 */
data class SourceDto(
    @SerializedName("id")
    val id: String?,
    
    @SerializedName("name")
    val name: String
)
