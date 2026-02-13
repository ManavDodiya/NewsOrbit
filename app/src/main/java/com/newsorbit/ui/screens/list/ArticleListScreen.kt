package com.newsorbit.ui.screens.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.newsorbit.domain.model.Article
import com.newsorbit.ui.components.*

/**
 * Article list screen displaying news articles.
 * Supports pull-to-refresh and pagination.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListScreen(
    onArticleClick: (Article) -> Unit,
    viewModel: ArticleListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("NewsOrbit") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = false),
            onRefresh = { viewModel.refresh() },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is ArticleListViewModel.UiState.Loading -> {
                    LoadingShimmer()
                }
                
                is ArticleListViewModel.UiState.Success -> {
                    ArticleList(
                        articles = state.articles,
                        onArticleClick = onArticleClick,
                        onLoadMore = { viewModel.loadMoreArticles() },
                        listState = listState
                    )
                }
                
                is ArticleListViewModel.UiState.Error -> {
                    ErrorView(
                        message = state.message,
                        onRetry = { viewModel.loadArticles() }
                    )
                }
                
                is ArticleListViewModel.UiState.Empty -> {
                    EmptyView()
                }
            }
        }
    }
}

/**
 * Composable displaying list of articles with pagination.
 */
@Composable
private fun ArticleList(
    articles: List<Article>,
    onArticleClick: (Article) -> Unit,
    onLoadMore: () -> Unit,
    listState: androidx.compose.foundation.lazy.LazyListState
) {
    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(articles, key = { it.url }) { article ->
            ArticleCard(
                article = article,
                onClick = { onArticleClick(article) }
            )
        }
        
        // Pagination loading indicator
        item {
            // Detect when user scrolls near the end
            LaunchedEffect(listState) {
                snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                    .collect { lastVisibleIndex ->
                        if (lastVisibleIndex != null && lastVisibleIndex >= articles.size - 3) {
                            onLoadMore()
                        }
                    }
            }
            
            // Show loading indicator at bottom when loading more
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
