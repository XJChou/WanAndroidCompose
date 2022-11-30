package com.zxj.share.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.zxj.share.ShareRoute

const val shareRoute = "shareRoute"

fun NavController.navigateToShare() {
    navigate(shareRoute)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.shareScreen(
    onBack: () -> Unit,
    onBrowser: (String) -> Unit
) {
    composable(shareRoute) {
        ShareRoute(
            onBack = onBack,
            onBrowser = onBrowser
        )
    }
}