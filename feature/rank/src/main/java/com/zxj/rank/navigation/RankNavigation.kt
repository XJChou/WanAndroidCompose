package com.zxj.rank.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zxj.rank.RankRoute

const val rankRoute = "rankRoute"

fun NavController.navigateToRank() {
    navigate(rankRoute)
}

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