package com.newsorbit.domain.model

/**
 * Domain model representing a news article.
 * This is the UI-friendly model used throughout the app.
 */
data class Article(
    val url: String,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val source: String,
    val author: String?,
    val publishedAt: String,
    val content: String?
)
