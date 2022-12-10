package com.zxj.rank.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.zxj.rank.RankRoute

const val rankRoute = "rankRoute"

fun NavController.navigateToRank() {
    navigate(rankRoute)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.rankScreen(
    onBack: () -> Unit
) {
    composable(rankRoute) {
        RankRoute(
            modifier = Modifier.fillMaxSize(),
            onBack = onBack
        )
    }
}