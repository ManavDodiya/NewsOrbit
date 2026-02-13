package com.newsorbit.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.newsorbit.domain.model.Article
import com.newsorbit.ui.screens.detail.ArticleDetailScreen
import com.newsorbit.ui.screens.list.ArticleListScreen
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Navigation routes for the app.
 */
object Routes {
    const val ARTICLE_LIST = "article_list"
    const val ARTICLE_DETAIL = "article_detail/{articleJson}"
    
    fun articleDetail(article: Article): String {
        val json = Gson().toJson(article)
        val encoded = URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
        return "article_detail/$encoded"
    }
}

/**
 * Navigation graph for the app.
 */
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.ARTICLE_LIST
    ) {
        // Article list screen
        composable(Routes.ARTICLE_LIST) {
            ArticleListScreen(
                onArticleClick = { article ->
                    navController.navigate(Routes.articleDetail(article))
                }
            )
        }
        
        // Article detail screen
        composable(Routes.ARTICLE_DETAIL) { backStackEntry ->
            val articleJson = backStackEntry.arguments?.getString("articleJson")
            val decodedJson = URLDecoder.decode(articleJson, StandardCharsets.UTF_8.toString())
            val article = Gson().fromJson(decodedJson, Article::class.java)
            
            ArticleDetailScreen(
                article = article,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
