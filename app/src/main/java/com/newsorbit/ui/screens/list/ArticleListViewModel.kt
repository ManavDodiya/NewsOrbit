package com.newsorbit.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsorbit.data.repository.NewsRepository
import com.newsorbit.domain.model.Article
import com.newsorbit.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for article list screen.
 * Manages UI state and handles user interactions.
 */
@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    private var currentPage = 1
    private val articles = mutableListOf<Article>()
    private var isLoadingMore = false
    
    init {
        loadArticles()
    }
    
    /**
     * Load initial articles.
     */
    fun loadArticles() {
        currentPage = 1
        articles.clear()
        fetchArticles(page = currentPage, forceRefresh = false)
    }
    
    /**
     * Refresh articles (pull-to-refresh).
     */
    fun refresh() {
        currentPage = 1
        articles.clear()
        fetchArticles(page = currentPage, forceRefresh = true)
    }
    
    /**
     * Load more articles for pagination.
     */
    fun loadMoreArticles() {
        if (isLoadingMore) return
        
        isLoadingMore = true
        currentPage++
        fetchArticles(page = currentPage, forceRefresh = false)
    }
    
    /**
     * Fetch articles from repository.
     */
    private fun fetchArticles(page: Int, forceRefresh: Boolean) {
        viewModelScope.launch {
            repository.getArticles(page, forceRefresh).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        if (page == 1) {
                            _uiState.value = UiState.Loading
                        }
                    }
                    
                    is Result.Success -> {
                        isLoadingMore = false
                        
                        if (page == 1) {
                            articles.clear()
                        }
                        
                        articles.addAll(result.data)
                        
                        _uiState.value = if (articles.isEmpty()) {
                            UiState.Empty
                        } else {
                            UiState.Success(articles.toList())
                        }
                    }
                    
                    is Result.Error -> {
                        isLoadingMore = false
                        
                        if (articles.isEmpty()) {
                            _uiState.value = UiState.Error(result.message)
                        } else {
                            // Keep showing current articles even if pagination fails
                            _uiState.value = UiState.Success(articles.toList())
                        }
                    }
                }
            }
        }
    }
    
    /**
     * UI state sealed class.
     */
    sealed class UiState {
        data object Loading : UiState()
        data class Success(val articles: List<Article>) : UiState()
        data class Error(val message: String) : UiState()
        data object Empty : UiState()
    }
}
