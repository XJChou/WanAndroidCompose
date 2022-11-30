package com.zxj.score.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.zxj.score.ScoreRoute

const val scoreRoute = "scoreRoute"

fun NavController.navigateToScore() {
    navigate(scoreRoute)
}

@OptIn(ExperimentalAnimationApi::class)
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