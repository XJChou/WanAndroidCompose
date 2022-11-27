package com.zxj.score.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zxj.score.ScoreRoute

const val scoreRoute = "scoreRoute"

fun NavController.navigateToScore() {
    navigate(scoreRoute)
}

fun NavGraphBuilder.scoreScreen(
    onBack: () -> Unit,
    onBrowser: (String) -> Unit
) {
    composable(scoreRoute) {
        ScoreRoute(
            onBack = onBack,
            modifier = Modifier.fillMaxSize(),
            onBrowser = onBrowser
        )
    }
}