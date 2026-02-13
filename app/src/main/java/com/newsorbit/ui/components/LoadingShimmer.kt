package com.newsorbit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.newsorbit.ui.theme.ShimmerColorShades

/**
 * Shimmer loading effect for skeleton UI.
 * Provides a smooth animated gradient to indicate loading state.
 */
@Composable
fun LoadingShimmer(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        repeat(5) {
            ShimmerArticleCard()
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ShimmerArticleCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            // Image placeholder
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .shimmerEffect()
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Text placeholders
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Title placeholder
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .shimmerEffect()
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Description placeholder
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(16.dp)
                        .shimmerEffect()
                )
                
                Spacer(modifier = Modifier.weight(1f))
                
                // Source placeholder
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(14.dp)
                        .shimmerEffect()
                )
            }
        }
    }
}

/**
 * Shimmer effect modifier.
 * Creates an animated gradient background.
 */
private fun Modifier.shimmerEffect(): Modifier = composed {
    this.background(
        brush = Brush.linearGradient(
            colors = ShimmerColorShades,
            start = Offset(0f, 0f),
            end = Offset(1000f, 1000f)
        ),
        shape = RoundedCornerShape(4.dp)
    )
}
