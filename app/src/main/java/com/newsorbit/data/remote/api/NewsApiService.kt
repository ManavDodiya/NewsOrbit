package com.newsorbit.data.remote.api

import com.newsorbit.data.remote.dto.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit API service for NewsAPI.org endpoints.
 */
interface NewsApiService {
    
    /**
     * Fetch top headlines from NewsAPI.
     * 
     * @param country Country code (default: "us")
     * @param page Page number for pagination
     * @param pageSize Number of articles per page
     * @param apiKey API key for authentication
     * @return NewsResponseDto containing list of articles
     */
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey") apiKey: String
    ): NewsResponseDto
}
